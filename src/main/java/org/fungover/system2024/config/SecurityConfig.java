package org.fungover.system2024.config;

<<<<<<< HEAD
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
=======
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
>>>>>>> origin/main
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
<<<<<<< HEAD
=======
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
>>>>>>> origin/main

@Configuration
@EnableWebSecurity
public class SecurityConfig {

<<<<<<< HEAD
=======
    private final String baseUrl;

    public SecurityConfig(@Value("${app.baseUrl}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

>>>>>>> origin/main
    @Bean
    @Profile("development")
    public SecurityFilterChain developmentSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults()) // Enable CSRF protection
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Allow all requests for development
                )
                .httpBasic(Customizer.withDefaults()); // Use HTTP Basic authentication for development
<<<<<<< HEAD

        // TODO: Add specific endpoint security rules here
        // Example:
        // .authorizeHttpRequests(auth -> auth
        //         .requestMatchers("/public/**").permitAll() // Public endpoints
        //         .anyRequest().authenticated() // All other endpoints require authentication
        // )

        // TODO: Configure OAuth2 login with GitHub here
        // Example:
        // .oauth2Login(Customizer.withDefaults()); // OAuth2 login configuration for future use

=======
>>>>>>> origin/main
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
<<<<<<< HEAD
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
=======
                .oauth2Login(Customizer.withDefaults())
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(logoutSuccessHandler())
                )
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/oauth2/authorization/github"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        ));
        return http.build();
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        return ((request, response, authentication) ->
                response.sendRedirect(baseUrl + "/login"));
    }
>>>>>>> origin/main
}