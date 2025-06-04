package info.jab.latency;

import info.jab.latency.config.RestAssuredTestConfig;
import info.jab.latency.util.TestDataLoader;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class GreekGodsApiSmokeTest extends RestAssuredTestConfig {

    @Autowired
    private TestDataLoader testDataLoader;

    @BeforeEach
    void setUpTestData() {
        // This will fail until we create the database schema
        // testDataLoader.loadGreekGodsTestData();
    }

    @Test
    void shouldSuccessfullyRetrieveCompleteListOfGreekGodNames() {
        // ATDD Red Phase: This test WILL FAIL - no API endpoint exists yet
        
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
        
        // Verify response time is under 1 second
        assertTrue(duration.toMillis() < 1000, 
                "Response time should be under 1 second, was: " + duration.toMillis() + "ms");
        
        // Verify we got exactly 20 god names
        assertEquals(20, response.size(), "Should return exactly 20 Greek god names");
        
        // Verify expected gods are in the response
        List<String> expectedGods = testDataLoader.getExpectedGreekGodNames();
        assertTrue(response.containsAll(expectedGods), 
                "Response should contain all expected Greek god names");
    }

    @Test
    void shouldReturnJsonArrayFormat() {
        // ATDD Red Phase: This test WILL FAIL - no API endpoint exists yet
        
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
        // ATDD Red Phase: This test WILL FAIL - no API endpoint exists yet
        
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