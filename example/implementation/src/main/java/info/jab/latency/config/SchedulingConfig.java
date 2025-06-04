package info.jab.latency.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration class for enabling/disabling Spring scheduling.
 * 
 * Scheduling can be controlled via the property:
 * spring.scheduling.enabled=true/false (default: true)
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(
    name = "spring.scheduling.enabled", 
    havingValue = "true", 
    matchIfMissing = true
)
public class SchedulingConfig {
    // Configuration class - no additional code needed
} 