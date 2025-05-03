package org.fungover.system2024.user;

import org.fungover.system2024.user.dto.UserDto;
import org.fungover.system2024.user.dto.UserUpdateDto;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @QueryMapping
    Iterable<UserDto> users() {
        return userService.getAllUsers();
    }

    @MutationMapping
    public Boolean updateUser(@Argument("Input") UserUpdateDto input) {
        userService.updateUser(input);
        return true;
    }
}
