package info.jab.latency.service;

import info.jab.latency.entity.GreekGod;
import info.jab.latency.repository.GreekGodsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 * Background synchronization service for Greek Gods data from external API.
 * 
 * This service runs periodically to sync data from external sources.
 * Configured with simple HTTP timeout support and basic error handling.
 */
@Service
public class BackgroundSyncService {

    private static final Logger logger = LoggerFactory.getLogger(BackgroundSyncService.class);
    private final RestClient restClient;
    private final String apiEndpoint;
    private final int timeoutMs;
    private final GreekGodsRepository greekGodsRepository;
    private final boolean syncEnabled;

    public BackgroundSyncService(
            @Value("${external-api.greek-gods.base-url}") String baseUrl,
            @Value("${external-api.greek-gods.endpoint}") String endpoint,
            @Value("${external-api.greek-gods.timeout:30000}") int timeoutMs,
            @Value("${background-sync.greek-gods.enabled:true}") boolean syncEnabled,
            GreekGodsRepository greekGodsRepository) {
        
        this.apiEndpoint = endpoint;
        this.timeoutMs = timeoutMs;
        this.syncEnabled = syncEnabled;
        this.greekGodsRepository = greekGodsRepository;
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(new SimpleClientHttpRequestFactory() {{
                    setConnectTimeout(timeoutMs);
                    setReadTimeout(timeoutMs);
                }})
                .build();
        
        logger.info("BackgroundSyncService configured: baseUrl={}, endpoint={}, timeout={}ms, syncEnabled={}", 
                   baseUrl, endpoint, timeoutMs, syncEnabled);
    }

    //TODO Scheduled & Transactional doesn´t work together
    /**
     * Synchronizes Greek Gods data from external API to database.
     * Scheduling configured via application properties.
     */
    @Scheduled(fixedRateString = "${background-sync.greek-gods.fixed-rate:1800000}",
               initialDelayString = "${background-sync.greek-gods.initial-delay:60000}")
    @Transactional
    public void synchronizeData() {
        if (!syncEnabled) {
            logger.debug("Background synchronization skipped - disabled via configuration");
            return;
        }
        
        long startTime = System.currentTimeMillis();
        String syncId = generateSyncId();
        
        logger.info("[SYNC-{}] Starting background synchronization", syncId);
        
        try {
            // Fetch data from external API
            List<Map<String, Object>> externalData = fetchDataFromExternalAPI();
            logger.info("[SYNC-{}] Fetched {} records from external API", syncId, externalData.size());
            
            // Transform external data to GreekGod entities
            List<GreekGod> greekGods = transformToGreekGods(externalData);
            logger.info("[SYNC-{}] Transformed {} records to GreekGod entities", syncId, greekGods.size());
            
            // Save transformed data to database
            SyncResult syncResult = saveGreekGodsToDatabase(greekGods);
            
            // Log successful completion
            long duration = System.currentTimeMillis() - startTime;
            logger.info("[SYNC-{}] Completed successfully in {}ms. New: {}, Duplicates: {}, Errors: {}", 
                       syncId, duration, syncResult.inserted, syncResult.duplicatesSkipped, syncResult.errors);
            
        } catch (RestClientException e) {
            long duration = System.currentTimeMillis() - startTime;
            logger.error("[SYNC-{}] Failed due to API error after {}ms: {}", syncId, duration, e.getMessage());
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            logger.error("[SYNC-{}] Failed due to unexpected error after {}ms: {}", syncId, duration, e.getMessage(), e);
        }
    }

    /**
     * Fetches Greek Gods data from external JSON server.
     */
    private List<Map<String, Object>> fetchDataFromExternalAPI() {
        logger.debug("Fetching data from external API: {}", apiEndpoint);
        
        List<Map<String, Object>> result = restClient
                .get()
                .uri(apiEndpoint)
                .retrieve()
                .body(List.class);
        
        if (result == null) {
            logger.warn("External API returned null response");
            return List.of();
        }
        
        logger.debug("Successfully fetched {} records from external API", result.size());
        return result;
    }

    /**
     * Transforms external API data format to GreekGod entities.
     */
    private List<GreekGod> transformToGreekGods(List<Map<String, Object>> externalData) {
        if (externalData.isEmpty()) {
            logger.warn("No external data to transform");
            return List.of();
        }
        
        List<GreekGod> transformedGods = externalData.stream()
                .map(this::mapToGreekGod)
                .filter(god -> god != null)
                .collect(Collectors.toList());
        
        if (transformedGods.size() < externalData.size()) {
            int skipped = externalData.size() - transformedGods.size();
            logger.warn("Skipped {} invalid records during transformation", skipped);
        }
        
        return transformedGods;
    }

    /**
     * Maps a single external API record to a GreekGod entity.
     */
    private GreekGod mapToGreekGod(Map<String, Object> externalRecord) {
        try {
            String name = extractName(externalRecord);
            
            if (name == null || name.trim().isEmpty()) {
                logger.debug("Skipping record with missing name: {}", externalRecord);
                return null;
            }
            
            return new GreekGod(name.trim());
            
        } catch (Exception e) {
            logger.error("Failed to transform record: {} - {}", externalRecord, e.getMessage());
            return null;
        }
    }

    /**
     * Extracts the name field from external API record.
     */
    private String extractName(Map<String, Object> record) {
        String[] nameFields = {"name", "godName", "fullName", "title", "deity"};
        
        for (String field : nameFields) {
            Object value = record.get(field);
            if (value != null && !value.toString().trim().isEmpty()) {
                return value.toString();
            }
        }
        
        return null;
    }

    /**
     * Saves GreekGod entities to database with duplicate detection.
     */
    private SyncResult saveGreekGodsToDatabase(List<GreekGod> greekGods) {
        if (greekGods.isEmpty()) {
            logger.warn("No entities to save");
            return new SyncResult();
        }
        
        SyncResult result = new SyncResult();
        List<GreekGod> newGods = new ArrayList<>();
        
        // Check for duplicates
        for (GreekGod god : greekGods) {
            try {
                if (greekGodsRepository.existsByName(god.getName())) {
                    result.duplicatesSkipped++;
                } else {
                    newGods.add(god);
                }
            } catch (Exception e) {
                logger.error("Error checking existence for god '{}': {}", god.getName(), e.getMessage());
                result.errors++;
            }
        }
        
        // Save new gods
        if (!newGods.isEmpty()) {
            logger.info("Saving {} new gods to database", newGods.size());
            
            for (GreekGod god : newGods) {
                try {
                    greekGodsRepository.save(god);
                    result.inserted++;
                } catch (Exception e) {
                    logger.error("Failed to save god '{}': {}", god.getName(), e.getMessage());
                    result.errors++;
                }
            }
        }
        
        return result;
    }

    /**
     * Generates a unique sync ID for tracking.
     */
    private String generateSyncId() {
        return String.format("%d-%04d", 
                           System.currentTimeMillis() / 1000, 
                           (int)(Math.random() * 10000));
    }

    /**
     * Result statistics for synchronization operations.
     */
    private static class SyncResult {
        int inserted = 0;
        int duplicatesSkipped = 0;
        int errors = 0;
    }
} 