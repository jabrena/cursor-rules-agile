package info.jab.latency.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class TestDataLoader {

    private final JdbcTemplate jdbcTemplate;

    public TestDataLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void loadGreekGodsTestData() {
        try {
            ClassPathResource resource = new ClassPathResource("test-data/greek-gods-seed.sql");
            String sql = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            
            // Split by semicolon and execute each statement
            Arrays.stream(sql.split(";"))
                    .map(String::trim)
                    .filter(statement -> !statement.isEmpty() && !statement.startsWith("--"))
                    .forEach(jdbcTemplate::execute);
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data", e);
        }
    }

    public List<String> getExpectedGreekGodNames() {
        return List.of(
                "Zeus", "Hera", "Poseidon", "Demeter", "Athena",
                "Apollo", "Artemis", "Ares", "Aphrodite", "Hephaestus",
                "Hermes", "Dionysus", "Hades", "Persephone", "Hestia",
                "Hebe", "Hecate", "Iris", "Nike", "Tyche"
        );
    }

    public int getExpectedGodCount() {
        return 20;
    }
} 