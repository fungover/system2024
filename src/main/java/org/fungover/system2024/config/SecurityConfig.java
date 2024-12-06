package org.fungover.system2024.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Allow all requests for development
                )
                .httpBasic(Customizer.withDefaults()); // Use HTTP Basic authentication for development

        // TODO: Add specific endpoint security rules here
        // Example:
        // .authorizeHttpRequests(auth -> auth
        //         .requestMatchers("/public/**").permitAll() // Public endpoints
        //         .anyRequest().authenticated() // All other endpoints require authentication
        // )

        // TODO: Configure OAuth2 login with GitHub here
        // Example:
        // .oauth2Login(Customizer.withDefaults()); // OAuth2 login configuration for future use

        return http.build();
    }
}