package info.jab.latency.repository;

import info.jab.latency.entity.GreekGod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Testcontainers
class GreekGodsRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        
        // Disable Flyway for tests - we'll use Flyway migration manually if needed
        registry.add("spring.flyway.enabled", () -> "true");
    }

    @Autowired
    private GreekGodsRepository greekGodsRepository;

    @Test
    void testFindAllGodNames() {
        // Arrange: Expected 20 Greek god names based on requirements
        int expectedGodCount = 20;

        // Act: Retrieve all god names from repository
        List<String> godNames = greekGodsRepository.findAllGodNames();

        // Assert: Verify we get exactly 20 god names
        assertThat(godNames)
                .isNotNull()
                .hasSize(expectedGodCount)
                .doesNotContainNull()
                .allSatisfy(name -> assertThat(name).isNotBlank());

        // Verify some expected Greek god names are present
        assertThat(godNames)
                .containsAnyOf("Zeus", "Hera", "Poseidon", "Athena", "Apollo", "Artemis");
    }

    @Test
    void testRepositoryIsNotNull() {
        // Basic smoke test to ensure repository is properly injected
        assertThat(greekGodsRepository).isNotNull();
    }
} 