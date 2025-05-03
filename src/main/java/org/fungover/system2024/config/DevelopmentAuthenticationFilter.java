package org.fungover.system2024.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.fungover.system2024.user.entity.User;
import org.fungover.system2024.user.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Profile("development")
public class DevelopmentAuthenticationFilter extends GenericFilterBean {

    private final UserRepository userRepository;

    public DevelopmentAuthenticationFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        User user = userRepository.findByEmail("development@example.com")
                .orElseThrow(() -> new RuntimeException("Development user not found by email"));

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("email", user.getEmail());

        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_USER"));

        OAuth2User oAuth2DevelopmentUser = new DefaultOAuth2User(
                authorities, attributes, "email");

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                oAuth2DevelopmentUser, "N/A", authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
