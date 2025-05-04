package org.fungover.system2024.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fungover.system2024.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DevelopmentAuthenticationFilterTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @Mock
    private DevelopmentAuthenticationFilterService developmentAuthenticationFilterService;

    private DevelopmentAuthenticationFilter filter;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("development@example.com");

        filter = new DevelopmentAuthenticationFilter(developmentAuthenticationFilterService);
    }

    @Test
    void testDoFilter() throws ServletException, IOException {
        DevelopmentAuthenticationFilterService service = new DevelopmentAuthenticationFilterService(null);
        DefaultOAuth2User oAuth2User = service.createOAuth2User(user);

        when(developmentAuthenticationFilterService.getUser()).thenReturn(user);
        when(developmentAuthenticationFilterService.createOAuth2User(user)).thenReturn(oAuth2User);

        SecurityContextHolder.clearContext();

        filter.doFilter(request, response, chain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        assertNotNull(authentication);
        assertTrue(authentication.isAuthenticated());
        assertInstanceOf(UsernamePasswordAuthenticationToken.class, authentication);
        assertEquals("development@example.com", ((OAuth2User) authentication.getPrincipal()).getAttribute("email"));

        verify(developmentAuthenticationFilterService).getUser();
        verify(developmentAuthenticationFilterService).createOAuth2User(user);
        verify(chain).doFilter(request, response);
    }

    @Test
    void testDoFilterWithException() throws ServletException, IOException {
        when(developmentAuthenticationFilterService.getUser())
                .thenThrow(new RuntimeException("Development user not found by email"));

        SecurityContextHolder.clearContext();

        ServletException servletException = assertThrows(ServletException.class, () ->
                filter.doFilter(request, response, chain));

        assertEquals("Development user not found by email", servletException.getCause().getMessage());
        assertInstanceOf(RuntimeException.class, servletException.getCause());

        verify(chain, never()).doFilter(request, response);
    }
}