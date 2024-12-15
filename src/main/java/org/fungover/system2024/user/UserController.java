package org.fungover.system2024.user;

import org.fungover.system2024.user.dto.UserDto;
import org.fungover.system2024.user.entity.User;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @QueryMapping
  Iterable<UserDto> users(){
    return userService.getAllUsers();
  }

  @PostMapping("/create")
  public String createUser(@RequestBody User user, Model model) {
    try {
      User savedUser = userService.saveUser(user);
      model.addAttribute("user", savedUser);
      return "user/create";
    } catch (Exception e) {
      throw new RuntimeException("Error creating user: " + e.getMessage(), e);
    }
  }

}
