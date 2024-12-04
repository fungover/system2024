package org.fungover.system2024.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Mock
    private OAuth2User oAuth2User;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void index() throws Exception {
        Map<String, Object> attributes = Collections.singletonMap("name", "Test User");
        when(oAuth2User.getAttributes()).thenReturn(attributes);
        when(oAuth2User.getAttribute("name")).thenReturn("Test User");
        when(oAuth2User.getName()).thenReturn("Test User");

        mvc.perform(get("/").with(oauth2Login().oauth2User(oAuth2User)))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("name", "Test User"));
    }
}