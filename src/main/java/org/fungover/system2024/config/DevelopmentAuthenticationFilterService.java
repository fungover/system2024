package org.fungover.system2024.config;

import org.fungover.system2024.user.entity.User;
import org.fungover.system2024.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DevelopmentAuthenticationFilterService {
    private final UserRepository userRepository;

    public DevelopmentAuthenticationFilterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser() {
        return userRepository.findByEmail("development@example.com")
                .orElseThrow(() -> new RuntimeException("Development user not found by email"));
    }

    public DefaultOAuth2User createOAuth2User(User user) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("email", user.getEmail());

        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_USER")
        );

        return new DefaultOAuth2User(authorities, attributes, "email");
    }

    public static Authentication createAuthentication(OAuth2User oAuth2DevelopmentUser) {
        return new UsernamePasswordAuthenticationToken(
                oAuth2DevelopmentUser, "N/A", oAuth2DevelopmentUser.getAuthorities());
    }
}
