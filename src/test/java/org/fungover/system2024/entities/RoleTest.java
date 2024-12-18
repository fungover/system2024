package org.fungover.system2024.entities;

import org.fungover.system2024.user.entity.Role;
import org.fungover.system2024.user.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {
    @Test
    void roleNameShouldNotBeNull() {
        Role role = new Role();
        role.setName("Admin");
        assertNotNull(role.getName());
    }

    @Test
    void roleNameShouldHaveMaxLength() {
        Role role = new Role();
        role.setName("A".repeat(255));
        assertDoesNotThrow(() -> role.getName().length() <= 255);
    }

    @Test
    void roleUsersShouldBeEmptyInitially() {
        Role role = new Role();
        assertTrue(role.getUsers().isEmpty());
    }

    @Test
    void rolePermissionsShouldBeEmptyInitially() {
        Role role = new Role();
        assertTrue(role.getPermissions().isEmpty());
    }

    @Test
    void addUserToRole() {
        Role role = new Role();
        User user = new User();
        role.getUsers().add(user);
        user.getRoles().add(role);
        assertTrue(role.getUsers().contains(user));
        assertTrue(user.getRoles().contains(role), "Bidirectional relationship not maintained");
    }


}