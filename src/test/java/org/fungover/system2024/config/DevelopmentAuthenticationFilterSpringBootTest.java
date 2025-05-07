package org.fungover.system2024.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MySQLContainer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("development")
class DevelopmentAuthenticationFilterSpringBootTest {
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
    MockMvc mockMvc;

    @Test
    void testDevelopmentAuthenticationFilter() throws Exception {
        String request = "{\"query\": \"{testUserAuth}\"}";

        MvcResult result = mockMvc.perform(post("/graphql").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Code653ht57t26234yp@example.com"));
    }
}
