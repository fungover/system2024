package org.fungover.system2024.user;

import org.fungover.system2024.user.entity.User;
import org.fungover.system2024.user.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "development@example.com", roles = "")
class UserControllerSpringBootTest {
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

    @Autowired
    private UserRepository userRepository;

    private void saveUserToDatabase() {
        User user1 = new User();
        user1.setFirst_name("Junior");
        user1.setLast_name("Senior");
        user1.setEmail("development@example.com");
        user1.setPassword("secretPassword");
        userRepository.save(user1);

        User user2 = new User();
        user2.setFirst_name("XJunior");
        user2.setLast_name("XSenior");
        user2.setEmail("xjunior@example.com");
        user2.setPassword("hackedPassword");
        userRepository.save(user2);
    }

    private static void authenticateOAuth2User(OAuth2User oAuth2User) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        oAuth2User, null, oAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static @NotNull OAuth2User getoAuth2User(String email) {
        OAuth2User oAuth2User = mock(OAuth2User.class);
        when(oAuth2User.getAttribute("email")).thenReturn(email);
        when(oAuth2User.getAuthorities()).thenReturn(List.of());
        return oAuth2User;
    }

    @Test
    void testUpdateUser() throws Exception {
        saveUserToDatabase();
        final OAuth2User oAuth2User = getoAuth2User("development@example.com");
        authenticateOAuth2User(oAuth2User);

        String request = "{\"query\":\"mutation {updateUser(Input: {firstName: \\\"Senior\\\", lastName: \\\"SuperSenior\\\", password: \\\"hackedPassword\\\"}) }\"}";

        mockMvc.perform(post("/graphql").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.updateUser").value(true))
                .andReturn();
    }

    @Test
    void testUpdateUserWhenNoAuthority() throws Exception {
        final OAuth2User oAuth2User = getoAuth2User("junior@example.com");
        authenticateOAuth2User(oAuth2User);

        String request = "{\"query\":\"mutation {updateUser(Input: {firstName: \\\"Senior\\\", lastName: \\\"SuperSenior\\\", password: \\\"hackedPassword\\\"}) }\"}";

        mockMvc.perform(post("/graphql").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.updateUser").doesNotExist())
                .andReturn();
    }
}