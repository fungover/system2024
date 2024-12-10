package org.fungover.system2024.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Profile("development")
    public SecurityFilterChain developmentSecurityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Development Security Filter Chain Active");
        http
                .csrf(Customizer.withDefaults()) // Enable CSRF protection
//                .csrf().disable() // Enable CSRF protection
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
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





    @Bean
    @Profile("production")
    public SecurityFilterChain productionSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults()) // Enable CSRF protection
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated() // Require authentication for all requests
                )
                .httpBasic(Customizer.withDefaults()); // Use HTTP Basic authentication

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