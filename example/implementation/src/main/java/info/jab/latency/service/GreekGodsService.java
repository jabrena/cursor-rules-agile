package info.jab.latency.service;

import info.jab.latency.repository.GreekGodsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for Greek Gods business logic.
 * 
 * Handles business operations related to Greek mythology data retrieval.
 * Following the C4 Model Component architecture: Controller → Service → Repository → Database
 * 
 * This service implements business logic for:
 * - Retrieving complete list of Greek god names
 * - Data validation and transformation
 * - Business rule enforcement
 * - Proper exception propagation to controller layer
 * 
 * Exception Propagation Strategy:
 * - Business logic exceptions are allowed to bubble up to controller
 * - No try-catch blocks suppress exceptions that should be handled globally
 * - Service methods declare potential exceptions in their contracts
 * - GlobalExceptionHandler will catch and handle all propagated exceptions
 * 
 * Architecture: Part of the Service Layer in the C4 Component Model
 * - Input: Business requests from Controller layer
 * - Output: Processed data ready for presentation
 * - Dependencies: Will integrate with GreekGodsRepository (future implementation)
 */
@Service
public class GreekGodsService {

    private static final Logger logger = LoggerFactory.getLogger(GreekGodsService.class);
    private static final int EXPECTED_GOD_COUNT = 20;

    private final GreekGodsRepository greekGodsRepository;

    /**
     * Constructor injection for GreekGodsRepository dependency.
     * 
     * @param greekGodsRepository the repository for database access
     */
    @Autowired
    public GreekGodsService(GreekGodsRepository greekGodsRepository) {
        this.greekGodsRepository = greekGodsRepository;
    }

    /**
     * Retrieves all Greek god names for API consumption.
     * 
     * Business logic:
     * - Returns complete dataset of Greek god names
     * - Ensures consistent ordering for API responses
     * - Validates data completeness (20 gods minimum)
     * - Propagates any business validation exceptions to controller
     * 
     * @return List<String> containing all Greek god names
     * @throws RuntimeException if business validation fails
     * @throws IllegalStateException if data integrity is compromised
     * 
     * @implNote Currently returns fake/hardcoded data following ATDD approach.
     *           Will be replaced with repository calls when database layer is implemented.
     *           Any exceptions from repository layer will propagate through this method.
     */
    public List<String> getAllGreekGodNames() {
        logger.debug("Retrieving all Greek god names from database via repository");
        
        try {
            // Use repository to fetch data from database
            List<String> greekGods = greekGodsRepository.findAllGodNames();
            
            // Business validation - ensure data integrity
            validateDataIntegrity(greekGods);
            
            logger.debug("Successfully retrieved {} Greek god names from database", greekGods.size());
            logger.trace("Greek god names: {}", greekGods);
            
            return greekGods;
            
        } catch (Exception ex) {
            // Log the exception but DO NOT catch it - let it propagate to GlobalExceptionHandler
            logger.error("Error occurred while retrieving Greek god names from database: {}", ex.getMessage());
            
            // Re-throw the exception to ensure proper propagation to controller layer
            // This is crucial for proper exception propagation strategy
            throw ex;
        }
    }

    /**
     * Validates that the Greek gods dataset is complete and meets business requirements.
     * 
     * Business rule: Must contain exactly 20 Greek god names
     * 
     * @return boolean true if dataset is valid and complete
     * @throws IllegalStateException if dataset validation fails
     */
    public boolean isDatasetComplete() {
        try {
            List<String> gods = getAllGreekGodNames();
            boolean isComplete = gods.size() == EXPECTED_GOD_COUNT;
            
            if (!isComplete) {
                throw new IllegalStateException(
                    String.format("Dataset integrity violation: expected %d gods, found %d", 
                                 EXPECTED_GOD_COUNT, gods.size())
                );
            }
            
            return isComplete;
        } catch (Exception ex) {
            logger.error("Dataset validation failed: {}", ex.getMessage());
            // Allow exception to propagate - no suppression
            throw ex;
        }
    }

    /**
     * Gets the count of available Greek god records.
     * 
     * @return int number of Greek god names available
     * @throws RuntimeException if data retrieval fails
     */
    public int getGreekGodsCount() {
        try {
            return getAllGreekGodNames().size();
        } catch (Exception ex) {
            logger.error("Failed to get Greek gods count: {}", ex.getMessage());
            // Allow exception to propagate - no suppression
            throw ex;
        }
    }
    
    /**
     * Private method to validate data integrity.
     * 
     * @param greekGods List of Greek god names to validate
     * @throws IllegalStateException if data validation fails
     * @throws RuntimeException if unexpected validation errors occur
     */
    private void validateDataIntegrity(List<String> greekGods) {
        if (greekGods == null) {
            throw new IllegalStateException("Greek gods data cannot be null");
        }
        
        if (greekGods.isEmpty()) {
            throw new IllegalStateException("Greek gods dataset cannot be empty");
        }
        
        if (greekGods.size() != EXPECTED_GOD_COUNT) {
            throw new IllegalStateException(
                String.format("Invalid dataset size: expected %d gods, found %d", 
                             EXPECTED_GOD_COUNT, greekGods.size())
            );
        }
        
        // Check for null or empty god names
        for (int i = 0; i < greekGods.size(); i++) {
            String godName = greekGods.get(i);
            if (godName == null || godName.trim().isEmpty()) {
                throw new IllegalStateException(
                    String.format("Invalid god name at index %d: cannot be null or empty", i)
                );
            }
        }
        
        logger.debug("Data integrity validation passed for {} gods", greekGods.size());
    }
} 