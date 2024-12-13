package org.fungover.system2024.entities;

import org.fungover.system2024.user.entity.Permission;
import org.fungover.system2024.user.entity.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PermissionTest {
    @Test
    void permissionNameShouldNotBeNull() {
        Permission permission = new Permission();
        permission.setName("Read Access");
        assertNotNull(permission.getName());
    }

    @Test
    void permissionDescriptionShouldNotBeNull() {
        Permission permission = new Permission();
        permission.setDescription("Allows read access to resources");
        assertNotNull(permission.getDescription());
    }

    @Test
    void permissionNameShouldHaveMaxLength() {
        Permission permission = new Permission();
        permission.setName("A".repeat(255));
        assertTrue(permission.getName().length() <= 255);
    }

    @Test
    void permissionDescriptionShouldHaveMaxLength() {
        Permission permission = new Permission();
        permission.setDescription("A".repeat(255));
        assertTrue(permission.getDescription().length() <= 255);
    }

    @Test
    void permissionRolesShouldBeEmptyInitially() {
        Permission permission = new Permission();
        assertTrue(permission.getRoles().isEmpty());
    }

    @Test
    void addRoleToPermission() {
        Permission permission = new Permission();
        Role role = new Role();
        permission.getRoles().add(role);
        assertTrue(permission.getRoles().contains(role));
    }
}