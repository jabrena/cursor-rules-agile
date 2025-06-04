package info.jab.latency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive Integration Tests for Greek Gods API.
 * 
 * This class provides full integration testing using Spring Boot test context
 * with TestRestTemplate for HTTP client testing and TestContainers for database integration.
 * 
 * Focuses on comprehensive system validation including:
 * - Complete response format validation
 * - Performance testing with timing assertions
 * - Error response format validation
 * - Concurrent request handling
 * - Load testing scenarios
 * - Overall system stability
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public class GreekGodsApiIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        // Configure base URL with Spring Boot test server port using @LocalServerPort and TestRestTemplate
        baseUrl = "http://localhost:" + port;
    }

    // Task 11.1: Spring Boot test context initialization (COMPLETED)
    @Test
    void shouldInitializeSpringBootTestContext() {
        // This test verifies that the Spring Boot test context initializes properly
        // and that all required components are loaded and ready
        
        ResponseEntity<List<String>> response = testRestTemplate.exchange(
                baseUrl + "/api/v1/gods/greek",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
        );
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // Task 11.2: Configure RestAssured with Spring Boot test server port (REPLACED with TestRestTemplate)
    @Test
    void shouldConfigureTestRestTemplateWithLocalServerPort() {
        // Verify TestRestTemplate is properly configured with @LocalServerPort
        assertNotNull(testRestTemplate);
        assertTrue(port > 0);
        assertTrue(baseUrl.contains(String.valueOf(port)));
        
        // Test that we can make requests using the configured port
        ResponseEntity<List<String>> response = testRestTemplate.exchange(
                baseUrl + "/api/v1/gods/greek",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
        );
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Task 11.3: Test complete response format validation (JSON array, 20 god names)
    @Test
    void shouldValidateCompleteResponseFormat() {
        ResponseEntity<List<String>> response = testRestTemplate.exchange(
                baseUrl + "/api/v1/gods/greek",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
        );
        
        // Validate HTTP status
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // Validate response body is not null
        assertNotNull(response.getBody());
        List<String> godNames = response.getBody();
        
        // Validate JSON array format with exactly 20 god names
        assertEquals(20, godNames.size(), "Should return exactly 20 Greek god names");
        
        // Validate all entries are non-null strings
        godNames.forEach(name -> {
            assertNotNull(name, "God name should not be null");
            assertFalse(name.trim().isEmpty(), "God name should not be empty");
        });
        
        // Validate expected gods are in the response
        List<String> expectedGods = List.of(
                "Zeus", "Hera", "Poseidon", "Demeter", "Athena",
                "Apollo", "Artemis", "Ares", "Aphrodite", "Hephaestus",
                "Hermes", "Dionysus", "Hades", "Persephone", "Hestia",
                "Hecate", "Pan", "Iris", "Nemesis", "Tyche"
        );
        assertTrue(godNames.containsAll(expectedGods), 
                "Response should contain all expected Greek god names");
    }

    // Task 11.4: Implement performance test with timing assertions (<1 second)
    @Test
    void shouldMeetPerformanceRequirements() {
        Instant start = Instant.now();
        
        ResponseEntity<List<String>> response = testRestTemplate.exchange(
                baseUrl + "/api/v1/gods/greek",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
        );
        
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        
        // Validate response is successful
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        // Validate performance requirement: response time under 1 second
        assertTrue(duration.toMillis() < 1000, 
                "Response time should be under 1 second, was: " + duration.toMillis() + "ms");
    }

    // Task 11.5: Validate error response format matches specification
    @Test
    void shouldValidateErrorResponseFormat() {
        // Test error response format with a non-existent endpoint
        // The GlobalExceptionHandler returns 500 INTERNAL_SERVER_ERROR for unmapped routes
        ResponseEntity<String> response = testRestTemplate.getForEntity(
                baseUrl + "/api/v1/gods/nonexistent", 
                String.class
        );
        
        // Validate error HTTP status (GlobalExceptionHandler returns 500 for unmapped routes)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        
        // Validate that we receive an error response body
        assertNotNull(response.getBody());
        
        // Validate content type is JSON (ProblemDetail format)
        String contentType = response.getHeaders().getFirst("Content-Type");
        assertNotNull(contentType);
        assertTrue(contentType.contains("application/json") || contentType.contains("application/problem+json"), 
                "Error response should be in JSON format");
        
        // Additional validation for RFC 7807 ProblemDetail format
        String responseBody = response.getBody();
        assertTrue(responseBody.contains("\"status\""), "Error response should contain status field");
        assertTrue(responseBody.contains("\"title\""), "Error response should contain title field");
        assertTrue(responseBody.contains("\"detail\""), "Error response should contain detail field");
    }

    // Task 11.6: Test concurrent request handling and system stability
    @Test
    void shouldHandleConcurrentRequestsStably() {
        int numberOfConcurrentRequests = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfConcurrentRequests);
        
        try {
            // Create concurrent requests
            List<CompletableFuture<ResponseEntity<List<String>>>> futures = IntStream.range(0, numberOfConcurrentRequests)
                    .mapToObj(i -> CompletableFuture.supplyAsync(() -> 
                            testRestTemplate.exchange(
                                    baseUrl + "/api/v1/gods/greek",
                                    HttpMethod.GET,
                                    null,
                                    new ParameterizedTypeReference<List<String>>() {}
                            ), executor))
                    .toList();
            
            // Wait for all requests to complete and validate results
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            
            // Validate all requests were successful
            futures.forEach(future -> {
                ResponseEntity<List<String>> response = future.join();
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(20, response.getBody().size());
            });
            
        } finally {
            executor.shutdown();
        }
    }

    // Task 11.7: Run load testing scenarios with multiple simultaneous requests
    @Test
    void shouldHandleLoadTestingScenarios() {
        int numberOfRequests = 50;
        int threadPoolSize = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
        
        try {
            Instant start = Instant.now();
            
            // Create load testing requests
            List<CompletableFuture<ResponseEntity<List<String>>>> futures = IntStream.range(0, numberOfRequests)
                    .mapToObj(i -> CompletableFuture.supplyAsync(() -> 
                            testRestTemplate.exchange(
                                    baseUrl + "/api/v1/gods/greek",
                                    HttpMethod.GET,
                                    null,
                                    new ParameterizedTypeReference<List<String>>() {}
                            ), executor))
                    .toList();
            
            // Wait for all requests to complete
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            
            Instant end = Instant.now();
            Duration totalDuration = Duration.between(start, end);
            
            // Validate all requests were successful
            int successfulRequests = 0;
            for (CompletableFuture<ResponseEntity<List<String>>> future : futures) {
                ResponseEntity<List<String>> response = future.join();
                if (response.getStatusCode() == HttpStatus.OK && 
                    response.getBody() != null && 
                    response.getBody().size() == 20) {
                    successfulRequests++;
                }
            }
            
            // Validate system stability under load
            assertEquals(numberOfRequests, successfulRequests, 
                    "All requests should be successful under load");
            
            // Validate reasonable total execution time (allowing for concurrency)
            assertTrue(totalDuration.toSeconds() < 30, 
                    "Load test should complete within reasonable time, was: " + totalDuration.toSeconds() + "s");
            
        } finally {
            executor.shutdown();
        }
    }

    // Task 11.8: Verify ALL acceptance criteria PASS - Complete system validation
    @Test
    void shouldPassAllAcceptanceCriteria() {
        // AC1: Successfully retrieve complete list of Greek god names
        ResponseEntity<List<String>> response = testRestTemplate.exchange(
                baseUrl + "/api/v1/gods/greek",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
        );
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<String> godNames = response.getBody();
        
        // AC2: API response time consistently under 1 second
        Instant start = Instant.now();
        testRestTemplate.exchange(
                baseUrl + "/api/v1/gods/greek",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
        );
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        assertTrue(duration.toMillis() < 1000, "Response time should be under 1 second");
        
        // AC3: Proper HTTP status codes (200 for success)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // AC4: Simple JSON array response format
        assertInstanceOf(List.class, godNames);
        godNames.forEach(name -> assertInstanceOf(String.class, name));
        
        // AC5: Complete dataset of 20 Greek god names
        assertEquals(20, godNames.size());
        List<String> expectedGods = List.of(
                "Zeus", "Hera", "Poseidon", "Demeter", "Athena",
                "Apollo", "Artemis", "Ares", "Aphrodite", "Hephaestus",
                "Hermes", "Dionysus", "Hades", "Persephone", "Hestia",
                "Hecate", "Pan", "Iris", "Nemesis", "Tyche"
        );
        assertTrue(godNames.containsAll(expectedGods));
    }
} 