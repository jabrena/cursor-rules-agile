package info.jab.latency.controller;

import info.jab.latency.service.GreekGodsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GreekGodsController.
 * 
 * Tests the controller layer independently without requiring Spring Boot context.
 * Validates business logic integration between Controller and Service layers.
 * 
 * This test class verifies:
 * - Controller endpoint response format
 * - Service layer integration
 * - HTTP status code handling
 * - JSON array response structure
 */
class GreekGodsControllerTest {

    private GreekGodsController controller;
    private GreekGodsService service;

    @BeforeEach
    void setUp() {
        service = new GreekGodsService();
        controller = new GreekGodsController(service);
    }

    @Test
    void testGetGreekGods_ReturnsCorrectResponse() {
        // When
        ResponseEntity<List<String>> response = controller.getGreekGods();

        // Then
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");
        
        List<String> greekGods = response.getBody();
        assertNotNull(greekGods, "Response body should not be null");
        assertEquals(20, greekGods.size(), "Should return exactly 20 Greek god names");
        
        // Verify some key gods are included
        assertTrue(greekGods.contains("Zeus"), "Should include Zeus");
        assertTrue(greekGods.contains("Hera"), "Should include Hera");
        assertTrue(greekGods.contains("Poseidon"), "Should include Poseidon");
        assertTrue(greekGods.contains("Athena"), "Should include Athena");
    }

    @Test
    void testGetGreekGods_ReturnsExpectedGods() {
        // When
        ResponseEntity<List<String>> response = controller.getGreekGods();

        // Then
        List<String> greekGods = response.getBody();
        assertNotNull(greekGods);
        
        // Verify the complete list contains all expected gods
        List<String> expectedGods = List.of(
            "Zeus", "Hera", "Poseidon", "Demeter", "Athena", "Apollo",
            "Artemis", "Ares", "Aphrodite", "Hephaestus", "Hermes", "Dionysus",
            "Hades", "Persephone", "Hestia", "Hecate", "Pan", "Iris", "Nemesis", "Tyche"
        );
        
        assertEquals(expectedGods.size(), greekGods.size(), "Should have same number of gods");
        assertTrue(greekGods.containsAll(expectedGods), "Should contain all expected gods");
    }

    @Test
    void testService_DatasetIsComplete() {
        // When
        boolean isComplete = service.isDatasetComplete();

        // Then
        assertTrue(isComplete, "Dataset should be complete with 20 gods");
    }

    @Test
    void testService_CorrectCount() {
        // When
        int count = service.getGreekGodsCount();

        // Then
        assertEquals(20, count, "Should count exactly 20 gods");
    }
} 