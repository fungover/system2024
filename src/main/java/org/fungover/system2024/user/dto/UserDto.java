package org.fungover.system2024.user.dto;

import org.fungover.system2024.user.entity.User;

public record UserDto(String name, String email) {

  public static UserDto from(User user) {
    return new UserDto(user.getFirst_name() + ' ' + user.getLast_name(), user.getEmail());
  }
}
