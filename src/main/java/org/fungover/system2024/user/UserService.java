package org.fungover.system2024.user;

import org.fungover.system2024.notification.NotificationService;
import org.fungover.system2024.user.dto.UserDto;
import org.fungover.system2024.user.entity.User;
import org.fungover.system2024.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
  private final UserRepository userRepository;

  private final NotificationService notificationService;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, NotificationService notificationService, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.notificationService = notificationService;
    this.passwordEncoder = passwordEncoder;
  }

  public Set<UserDto> getAllUsers(){
    return userRepository.findAll().stream()
        .map(UserDto::from)
        .collect(Collectors.toSet());
  }

  public User saveUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User savedUser = userRepository.save(user);
    notificationService.sendNotification(user.getFirst_name(), "profile created");
    return savedUser;
  }
}
