package org.fungover.system2024.user;

import lombok.extern.slf4j.Slf4j;
import org.fungover.system2024.exception.ResourceNotFoundException;
import org.fungover.system2024.notification.NotificationService;
import org.fungover.system2024.user.dto.UserDto;
import org.fungover.system2024.user.entity.User;
import org.fungover.system2024.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
  private final UserRepository userRepository;

  private final NotificationService notificationService;

  private final BCryptPasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, NotificationService notificationService, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.notificationService = notificationService;
    this.passwordEncoder = passwordEncoder;
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

  public User saveUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User savedUser = userRepository.save(user);
    notificationService.sendNotification(user.getFirst_name(), "profile created");
    return savedUser;
  }

  public User updateUser(Integer id, User newUserDetails) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      user.setFirst_name(newUserDetails.getFirst_name());
      user.setLast_name(newUserDetails.getLast_name());
      user.setEmail(newUserDetails.getEmail());
      user.setPassword(passwordEncoder.encode(newUserDetails.getPassword()));
      notificationService.sendNotification(user.getFirst_name(), "profile updated");
      return userRepository.save(user);
    } else {
      throw new RuntimeException("User not found with id: " + id);
    }
  }
}
