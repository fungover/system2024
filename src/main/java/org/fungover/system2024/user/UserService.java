package org.fungover.system2024.user;

import org.fungover.system2024.user.dto.UserDto;
import org.fungover.system2024.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Set<UserDto> getAllUsers(){
    return userRepository.findAll().stream()
        .map(UserDto::from)
        .collect(Collectors.toSet());
  }
}
