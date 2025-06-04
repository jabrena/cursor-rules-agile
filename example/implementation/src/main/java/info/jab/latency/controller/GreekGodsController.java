package info.jab.latency.controller;

import info.jab.latency.service.GreekGodsService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for Greek Gods API endpoints.
 * 
 * Handles HTTP requests for Greek mythology data retrieval.
 * Following OpenAPI 3.0.3 specification for /api/v1/gods/greek endpoint.
 * 
 * This controller implements the acceptance criteria:
 * - AC1: Successfully retrieve complete list of Greek god names
 * - AC3: Proper HTTP status codes (200 OK for success)
 * - AC4: Simple JSON array response format
 * - AC5: Complete dataset of 20 Greek god names
 * 
 * Architecture: Following C4 Model Component structure
 * Controller → Service → Repository → Database
 */
@RestController
@RequestMapping("/api/v1/gods")
public class GreekGodsController {

    private final GreekGodsService greekGodsService;

    /**
     * Constructor injection for GreekGodsService dependency.
     * 
     * @param greekGodsService Service layer for Greek gods business logic
     */
    public GreekGodsController(GreekGodsService greekGodsService) {
        this.greekGodsService = greekGodsService;
    }

    /**
     * GET /api/v1/gods/greek
     * 
     * Retrieves a complete list of Greek god names.
     * 
     * Response format: Simple JSON array of strings
     * Example: ["Zeus", "Hera", "Poseidon", ...]
     * 
     * @return ResponseEntity with List<String> containing 20 Greek god names
     *         HTTP 200 OK on success
     */
    @GetMapping(value = "/greek", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getGreekGods() {
        // Delegate to service layer for business logic
        List<String> greekGods = greekGodsService.getAllGreekGodNames();
        return ResponseEntity.ok(greekGods);
    }
} 