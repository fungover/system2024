package org.fungover.system2024.entities;

import org.fungover.system2024.user.entity.Role;
import org.junit.jupiter.api.Test;

import java.security.Permission;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoleTest {
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
        assertTrue(role.getName().length() <= 255);
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
        assertTrue(role.getUsers().contains(user));
    }


}