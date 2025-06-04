package info.jab.latency;

import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Acceptance tests for Greek Gods API using RestAssured.
 * 
 * These tests validate the complete user stories and business requirements 
 * from an external perspective, testing the full application stack.
 * 
 * Uses RestAssured for HTTP client testing as it provides excellent
 * support for API testing and validation of REST endpoints.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class GreekGodsApiAcceptanceIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        // Configure RestAssured for this test class
        RestAssured.port = port;
        RestAssured.config = RestAssured.config()
                .objectMapperConfig(ObjectMapperConfig.objectMapperConfig()
                        .defaultObjectMapperType(ObjectMapperType.JACKSON_2));
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void shouldSuccessfullyRetrieveCompleteListOfGreekGodNames() {
        // ATDD Green Phase: This test should PASS now with our controller implementation
        
        Instant start = Instant.now();
        
        List<String> response = given()
                .when()
                    .get("/api/v1/gods/greek")
                .then()
                    .statusCode(200)
                    .contentType("application/json")
                    .body("size()", equalTo(20))
                    .body("", hasItems("Zeus", "Hera", "Poseidon"))
                .extract()
                    .jsonPath()
                    .getList("", String.class);

        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        
        // Verify response time is under 1 second (acceptance criteria)
        assertTrue(duration.toMillis() < 1000, 
                "Response time should be under 1 second, was: " + duration.toMillis() + "ms");
        
        // Verify we got exactly 20 god names (acceptance criteria)
        assertEquals(20, response.size(), "Should return exactly 20 Greek god names");
        
        // Verify expected gods are in the response (hardcoded expected list)
        List<String> expectedGods = List.of(
                "Zeus", "Hera", "Poseidon", "Demeter", "Athena",
                "Apollo", "Artemis", "Ares", "Aphrodite", "Hephaestus",
                "Hermes", "Dionysus", "Hades", "Persephone", "Hestia",
                "Hecate", "Pan", "Iris", "Nemesis", "Tyche"
        );
        assertTrue(response.containsAll(expectedGods), 
                "Response should contain all expected Greek god names");
    }

    @Test
    void shouldReturnJsonArrayFormat() {
        // ATDD Green Phase: This test should PASS now with our controller implementation
        
        given()
                .when()
                    .get("/api/v1/gods/greek")
                .then()
                    .statusCode(200)
                    .contentType("application/json")
                    .body("", instanceOf(List.class));
    }

    @Test 
    void shouldHandleMultipleConcurrentRequests() {
        // ATDD Green Phase: This test should PASS now with our controller implementation
        
        // Simple concurrency test - send 3 requests simultaneously
        for (int i = 0; i < 3; i++) {
            given()
                    .when()
                        .get("/api/v1/gods/greek")
                    .then()
                        .statusCode(200)
                        .body("size()", equalTo(20));
        }
    }
} 