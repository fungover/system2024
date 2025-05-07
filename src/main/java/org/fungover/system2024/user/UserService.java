package org.fungover.system2024.user;

import lombok.extern.slf4j.Slf4j;
import org.fungover.system2024.exception.ResourceNotFoundException;
import org.fungover.system2024.user.dto.UserDto;
import org.fungover.system2024.user.dto.UserUpdateDto;
import org.fungover.system2024.user.entity.User;
import org.fungover.system2024.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Set<UserDto> getAllUsers() {

        Set<UserDto> users = userRepository.findAll().stream()
                .map(UserDto::from)
                .collect(Collectors.toSet());

        if (users.isEmpty()) {
            log.warn("No users found in database");
            throw new ResourceNotFoundException("No users found in database");
        }

        return users;
    }

    public void updateUser(UserUpdateDto userUpdateDto) {

        User user = getUser();

        if (userUpdateDto.firstName() != null && !userUpdateDto.firstName().isEmpty()) {
            user.setFirst_name(userUpdateDto.firstName());
        }

        if (userUpdateDto.lastName() != null && !userUpdateDto.lastName().isEmpty()) {
            user.setLast_name(userUpdateDto.lastName());
        }

        if (userUpdateDto.password() != null && !userUpdateDto.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdateDto.password()));
        }

        userRepository.save(user);
    }

    private User getUser() {
        String currentEmail = getAuthenticatedEmail();
        return userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exists"));
    }

    private static String getAuthenticatedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        return oAuth2User.getAttribute("email");
    }
}
