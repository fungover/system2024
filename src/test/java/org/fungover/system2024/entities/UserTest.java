package org.fungover.system2024.entities;

import org.fungover.system2024.user.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

 class UserTest {

     @Test
     void userRolesShouldBeEmptyInitially() {
         User user = new User();
         assertTrue(user.getRoles().isEmpty());
     }

     @Test
     void addRoleToUser() {
         User user = new User();
         Role role = new Role();
         user.getRoles().add(role);
         assertTrue(user.getRoles().contains(role));
     }

     @Test
     void userNameShouldNotExceedMaxLength() {
         User user = new User();
         user.setName("A".repeat(256));
         assertTrue(user.getName().length() <= 255);
     }

     @Test
     void userEmailShouldNotExceedMaxLength() {
         User user = new User();
         user.setEmail("A".repeat(101));
         assertTrue(user.getEmail().length() <= 100);
     }

     @Test
     void userPasswordShouldNotExceedMaxLength() {
         User user = new User();
         user.setPassword("A".repeat(61));
         assertTrue(user.getPassword().length() <= 60);
     }
}