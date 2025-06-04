package info.jab.latency.controller;

import info.jab.latency.service.GreekGodsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for Greek Gods API endpoints using MockMvc.
 * 
 * Tests the complete HTTP request/response cycle including:
 * - REST Controller endpoint mapping
 * - JSON response format validation
 * - HTTP status code verification
 * - Response content validation
 * 
 * Uses @WebMvcTest to test only the web layer with mocked dependencies.
 */
@WebMvcTest(GreekGodsController.class)
public class GreekGodsApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GreekGodsService greekGodsService;

    private final List<String> expectedGreekGods = List.of(
            "Zeus", "Hera", "Poseidon", "Demeter", "Athena", "Apollo",
            "Artemis", "Ares", "Aphrodite", "Hephaestus", "Hermes", "Dionysus",
            "Hades", "Persephone", "Hestia", "Hecate", "Pan", "Iris", "Nemesis", "Tyche"
    );

    /**
     * Integration test for GET /api/v1/gods/greek endpoint.
     * 
     * Validates:
     * - HTTP 200 OK status code
     * - Content-Type: application/json
     * - Response body is a JSON array
     * - Array contains string elements (god names)
     */
    @Test
    void testGetGreekGodsEndpoint_ReturnsJsonArrayOfGodNames() throws Exception {
        // Given
        when(greekGodsService.getAllGreekGodNames()).thenReturn(expectedGreekGods);

        // When & Then
        mockMvc.perform(get("/api/v1/gods/greek")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(20))
                .andExpect(jsonPath("$[0]").isString());
    }

    /**
     * Integration test verifying the endpoint returns exactly 20 Greek god names.
     * 
     * This test validates the complete dataset requirement from acceptance criteria.
     */
    @Test
    void testGetGreekGodsEndpoint_Returns20GodNames() throws Exception {
        // Given
        when(greekGodsService.getAllGreekGodNames()).thenReturn(expectedGreekGods);

        // When & Then
        mockMvc.perform(get("/api/v1/gods/greek")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(20))
                .andExpect(jsonPath("$[*]").isNotEmpty());
    }

    /**
     * Integration test for response format validation.
     * 
     * Validates that the response is a simple JSON array of strings,
     * not a complex object structure.
     */
    @Test
    void testGetGreekGodsEndpoint_ValidatesSimpleJsonArrayFormat() throws Exception {
        // Given
        when(greekGodsService.getAllGreekGodNames()).thenReturn(expectedGreekGods);

        // When & Then
        mockMvc.perform(get("/api/v1/gods/greek")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.status").doesNotExist());
    }

    /**
     * Integration test for HTTP method validation.
     * 
     * Ensures only GET requests are supported on this endpoint.
     */
    @Disabled
    @Test
    void testGreekGodsEndpoint_OnlySupportsGetMethod() throws Exception {
        // POST should return 405 Method Not Allowed
        mockMvc.perform(post("/api/v1/gods/greek")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());

        // PUT should return 405 Method Not Allowed
        mockMvc.perform(put("/api/v1/gods/greek")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());

        // DELETE should return 405 Method Not Allowed
        mockMvc.perform(delete("/api/v1/gods/greek"))
                .andExpect(status().isMethodNotAllowed());
    }

    /**
     * Integration test for non-existent endpoint validation.
     * 
     * Validates that requests to similar but incorrect paths return 404.
     */
    @Disabled
    @Test
    void testIncorrectEndpointPaths_Return404() throws Exception {
        // Test similar but incorrect paths
        mockMvc.perform(get("/api/v1/gods")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/v1/gods/roman")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/gods/greek")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
} 