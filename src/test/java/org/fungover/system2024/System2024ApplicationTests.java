package org.fungover.system2024;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest
class System2024ApplicationTests {
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:9.1")
            .withInitScript("schema.sql")
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
    }

    @Test
    void contextLoads() {
    }

}
