package org.fungover.system2024.config;

import org.fungover.system2024.user.entity.User;
import org.fungover.system2024.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DevelopmentAuthenticationFilterServiceTest {
    @Mock
    private UserRepository userRepository;

    private DevelopmentAuthenticationFilterService developmentAuthenticationFilterService;

    @BeforeEach
    void setUp() {
        developmentAuthenticationFilterService = new DevelopmentAuthenticationFilterService(userRepository);
    }

    @Test
    void testGetUser() {
        User user = new User();
        user.setEmail("development@example.com");

        when(userRepository.findByEmail("development@example.com"))
                .thenReturn(Optional.of(user));

        User validUser = developmentAuthenticationFilterService.getUser();

        assertNotNull(validUser);
        assertEquals("development@example.com", validUser.getEmail());
    }

    @Test
    void testGetUserNotFound() {
        when(userRepository.findByEmail("development@example.com"))
                .thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class, ()
                -> developmentAuthenticationFilterService.getUser());

        assertEquals("Development user not found by email", runtimeException.getMessage());
    }

    @Test
    void testCreateOAut2User() {
        User user = new User();
        user.setEmail("development@example.com");

        DefaultOAuth2User oAuth2User = developmentAuthenticationFilterService.createOAuth2User(user);
        List<String> authorities = oAuth2User.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        assertNotNull(oAuth2User);
        assertEquals("development@example.com", oAuth2User.getAttributes().get("email"));
        assertTrue(authorities.contains("ROLE_USER"));
    }

    @Test
    void testCreateAuthentication() {
        Map<String, Object> attributes = Map.of("email", "development@example.com");
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        OAuth2User oAuth2User = new DefaultOAuth2User(authorities, attributes, "email");

        Authentication authentication = DevelopmentAuthenticationFilterService.createAuthentication(oAuth2User);

        assertNotNull(authentication);
        assertEquals(authorities, authentication.getAuthorities());
        assertEquals(oAuth2User, authentication.getPrincipal());
        assertEquals("development@example.com", authentication.getName());
        assertEquals("N/A", authentication.getCredentials().toString());
        assertInstanceOf(UsernamePasswordAuthenticationToken.class, authentication);
    }

    @Test
    void testCreateAuthenticationNoAuthorities() {
        Map<String, Object> attributes = Map.of("email", "development@example.com");
        List<GrantedAuthority> authorities = Collections.emptyList();
        OAuth2User oAuth2User = new DefaultOAuth2User(authorities, attributes, "email");

        Authentication authentication = DevelopmentAuthenticationFilterService.createAuthentication(oAuth2User);

        assertNotNull(authentication);
        assertEquals(oAuth2User, authentication.getPrincipal());
        assertTrue(authentication.getAuthorities().isEmpty());
    }
}
