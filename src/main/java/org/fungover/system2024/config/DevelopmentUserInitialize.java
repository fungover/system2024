package org.fungover.system2024.config;

import org.fungover.system2024.user.entity.User;
import org.fungover.system2024.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("development")
public class DevelopmentUserInitialize {

    @Bean
    public CommandLineRunner initializeDevelopmentUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String email = "development@example.com";

            if (userRepository.findByEmail(email).isEmpty()) {
                User user = new User();
                user.setFirst_name("Junior");
                user.setLast_name("Developer");
                user.setEmail(email);
                user.setPassword(passwordEncoder.encode("password"));
                userRepository.save(user);
            }
        };
    }
}
