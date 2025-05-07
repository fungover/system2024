package org.fungover.system2024.config;

import org.fungover.system2024.user.entity.User;
import org.fungover.system2024.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("development")
class DevelopmentUserInitializeSpringBootTest {
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:9.1")
            .withDatabaseName("system24dbtest")
            .withUsername("myuser")
            .withPassword("secret");

    static {
        mysqlContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", mysqlContainer::getUsername);
        propertyRegistry.add("spring.datasource.password", mysqlContainer::getPassword);
        propertyRegistry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    UserRepository userRepository;

    @Test
    void testInitializeDevelopmentUser() {
        Optional<User> user = userRepository.findByEmail("Code653ht57t26234yp@example.com");

        assertTrue(user.isPresent());
        assertEquals("Junior", user.get().getFirst_name());
        assertEquals("Code653ht57t26234yp", user.get().getLast_name());
    }
}
