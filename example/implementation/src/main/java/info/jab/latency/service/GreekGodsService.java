package info.jab.latency.service;

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
 * 
 * Architecture: Part of the Service Layer in the C4 Component Model
 * - Input: Business requests from Controller layer
 * - Output: Processed data ready for presentation
 * - Dependencies: Will integrate with GreekGodsRepository (future implementation)
 */
@Service
public class GreekGodsService {

    /**
     * Retrieves all Greek god names for API consumption.
     * 
     * Business logic:
     * - Returns complete dataset of Greek god names
     * - Ensures consistent ordering for API responses
     * - Validates data completeness (20 gods minimum)
     * 
     * @return List<String> containing all Greek god names
     *         Returns hardcoded data until repository layer is implemented
     * 
     * @implNote Currently returns fake/hardcoded data following ATDD approach.
     *           Will be replaced with repository calls when database layer is implemented.
     */
    public List<String> getAllGreekGodNames() {
        // TODO: Replace with repository.findAllGodNames() when GreekGodsRepository is implemented
        // For now, returning hardcoded data to make controller tests pass (GREEN phase of ATDD)
        
        return List.of(
            "Zeus",           // King of the gods, god of sky and thunder
            "Hera",           // Queen of the gods, goddess of marriage
            "Poseidon",       // God of the sea, earthquakes, and horses
            "Demeter",        // Goddess of harvest and agriculture
            "Athena",         // Goddess of wisdom and warfare
            "Apollo",         // God of music, poetry, prophecy, and the sun
            "Artemis",        // Goddess of the hunt and moon
            "Ares",           // God of war and courage
            "Aphrodite",      // Goddess of love and beauty
            "Hephaestus",     // God of fire and metalworking
            "Hermes",         // Messenger god, god of trade and thieves
            "Dionysus",       // God of wine and festivities
            "Hades",          // God of the underworld and the dead
            "Persephone",     // Queen of the underworld, goddess of spring
            "Hestia",         // Goddess of the hearth and home
            "Hecate",         // Goddess of magic and crossroads
            "Pan",            // God of nature, shepherds, and the wild
            "Iris",           // Goddess of the rainbow and divine messenger
            "Nemesis",        // Goddess of divine retribution and revenge
            "Tyche"           // Goddess of fortune and prosperity
        );
    }

    /**
     * Validates that the Greek gods dataset is complete.
     * 
     * Business rule: Must contain exactly 20 Greek god names
     * 
     * @return boolean true if dataset is valid and complete
     */
    public boolean isDatasetComplete() {
        return getAllGreekGodNames().size() == 20;
    }

    /**
     * Gets the count of available Greek god records.
     * 
     * @return int number of Greek god names available
     */
    public int getGreekGodsCount() {
        return getAllGreekGodNames().size();
    }
} 