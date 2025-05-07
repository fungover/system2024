package org.fungover.system2024.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.fungover.system2024.user.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static org.fungover.system2024.config.DevelopmentAuthenticationFilterService.createAuthentication;

@Component
@Profile("development")
public class DevelopmentAuthenticationFilter extends GenericFilterBean {
    private final DevelopmentAuthenticationFilterService developmentAuthenticationFilterService;

    public DevelopmentAuthenticationFilter(DevelopmentAuthenticationFilterService developmentAuthenticationFilterService) {
        this.developmentAuthenticationFilterService = developmentAuthenticationFilterService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            User user = developmentAuthenticationFilterService.getUser();
            OAuth2User oAuth2DevelopmentUser = developmentAuthenticationFilterService.createOAuth2User(user);
            Authentication authentication = createAuthentication(oAuth2DevelopmentUser);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (RuntimeException exception) {
            throw new ServletException(exception);
        }
    }
}
