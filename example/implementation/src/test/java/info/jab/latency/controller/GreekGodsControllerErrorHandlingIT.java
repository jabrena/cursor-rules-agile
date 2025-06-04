package info.jab.latency.controller;

import info.jab.latency.service.GreekGodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for GreekGodsController error handling scenarios using MockMvc.
 * 
 * Tests the API's ability to handle various error conditions gracefully:
 * - Database connection failures
 * - Service layer exceptions
 * - Standardized error response format
 * 
 * Following ATDD approach - these tests will FAIL initially since error handling
 * is not implemented yet. This is the RED phase of Red-Green-Refactor cycle.
 * 
 * Uses @WebMvcTest to test only the web layer with mocked dependencies.
 * Architecture: Testing the complete request flow
 * HTTP Request → Controller → Service → [Mocked Error Scenarios]
 */
@WebMvcTest(GreekGodsController.class)
class GreekGodsControllerErrorHandlingIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GreekGodsService greekGodsService;

    @Test
    void should_handle_database_connection_failure_gracefully() throws Exception {
        // GIVEN: Service throws a RuntimeException simulating database connection failure
        when(greekGodsService.getAllGreekGodNames())
                .thenThrow(new RuntimeException("Database connection failed"));

        // NOTE: This test simulates a database connection failure scenario
        // Currently this test will FAIL because:
        // 1. No error handling is implemented in the controller
        // 2. No GlobalExceptionHandler exists to catch database errors
        // 3. The exception will bubble up and cause a 500 error with default Spring error format
        
        // WHEN: A request is made to the API during a simulated database failure
        // THEN: The API should handle the error gracefully
        
        // This test expects that when database errors occur, the API should:
        // - Return HTTP 500 Internal Server Error
        // - Provide a standardized error response format
        // - Include appropriate error message
        // - Not expose internal system details
        
        mockMvc.perform(get("/api/v1/gods/greek")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())  // Expected: 500 when database fails
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test 
    void should_handle_service_unavailable_gracefully() throws Exception {
        // GIVEN: Service throws an exception simulating service unavailability
        when(greekGodsService.getAllGreekGodNames())
                .thenThrow(new IllegalStateException("Service temporarily unavailable"));
        
        // This test simulates a scenario where the service layer fails
        // Expected behavior: Proper error response without system crash
        
        // NOTE: This test will FAIL initially - no error handling implemented yet
        // This is expected in ATDD Red-Green-Refactor cycle
        
        mockMvc.perform(get("/api/v1/gods/greek")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())  // Expected: 500 for service errors
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void should_return_proper_error_format_for_internal_errors() throws Exception {
        // GIVEN: Service throws a generic exception
        when(greekGodsService.getAllGreekGodNames())
                .thenThrow(new RuntimeException("Internal system error"));
        
        // This test validates the standardized error response format
        // Expected format: { "error": "message", "status": 500, "timestamp": "..." }
        
        // NOTE: This test will FAIL initially - no GlobalExceptionHandler exists yet
        // This confirms we're following ATDD approach correctly
        
        mockMvc.perform(get("/api/v1/gods/greek")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())  // Expected: 500 for internal errors
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.status").isNumber())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void should_not_expose_internal_system_details() throws Exception {
        // GIVEN: Service throws an exception with sensitive internal details
        when(greekGodsService.getAllGreekGodNames())
                .thenThrow(new RuntimeException("Connection to database server db-prod-001.internal.company.com:5432 failed"));
        
        // This test ensures that internal system details are not exposed to clients
        // Error messages should be sanitized for security
        
        mockMvc.perform(get("/api/v1/gods/greek")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").exists())
                // Should NOT contain internal server details
                .andExpect(jsonPath("$.error").value(not(containsString("db-prod-001.internal"))))
                .andExpect(jsonPath("$.message").value(not(containsString("5432"))));
    }
} 