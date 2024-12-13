package org.fungover.system2024.user;

import lombok.extern.slf4j.Slf4j;
import org.fungover.system2024.user.dto.UserDto;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @QueryMapping
  Iterable<UserDto> users(){ return userService.getAllUsers(); }
}
