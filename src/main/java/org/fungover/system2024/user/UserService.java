package org.fungover.system2024.user;

import lombok.extern.slf4j.Slf4j;
import org.fungover.system2024.exception.ResourceNotFoundException;
import org.fungover.system2024.user.dto.UserDto;
import org.fungover.system2024.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
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
}
