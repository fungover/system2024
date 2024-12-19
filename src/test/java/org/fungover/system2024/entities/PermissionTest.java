package org.fungover.system2024.entities;

import jakarta.validation.*;
import org.fungover.system2024.user.entity.Permission;
import org.fungover.system2024.user.entity.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PermissionTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void permissionNameShouldNotBeNull() {
        Permission permission = new Permission();
        permission.setName("Read Access");
        permission.setDescription("Allows read access to resources");
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);
        assertTrue(violations.isEmpty(), "Expected no violations for valid name");
        assertNotNull(permission.getName());
    }

    @Test
    void permissionDescriptionShouldNotBeNull() {
        Permission permission = new Permission();
        permission.setName("Read Access");
        permission.setDescription("Allows read access to resources");
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);
        assertTrue(violations.isEmpty(), "Expected no violations for valid description");
        assertNotNull(permission.getDescription());
    }

    @Test
    void permissionNameShouldHaveMaxLength() {
        Permission permission = new Permission();
        permission.setName("A".repeat(255));
        permission.setDescription("Valid Description");
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);
        assertTrue(violations.isEmpty(), "Expected no violations for valid name length");
        assertTrue(permission.getName().length() <= 255);
    }

    @Test
    void permissionDescriptionShouldHaveMaxLength() {
        Permission permission = new Permission();
        permission.setName("Valid Name");
        permission.setDescription("A".repeat(255));
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);
        assertTrue(violations.isEmpty(), "Expected no violations for valid description length");
        assertTrue(permission.getDescription().length() <= 255);
    }

    @Test
    void permissionRolesShouldBeEmptyInitially() {
        Permission permission = new Permission();
        permission.setName("ValidName");
        permission.setDescription("ValidDescription");
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);
        assertTrue(violations.isEmpty(), "Expected no violations for a new Permission object");
        assertTrue(permission.getRoles().isEmpty());
    }

    @Test
    void addRoleToPermission() {
        Permission permission = new Permission();
        permission.setName("ValidName");
        permission.setDescription("ValidDescription");
        Role role = new Role();
        role.setName("ValidRoleName");
        permission.getRoles().add(role);
        role.getPermissions().add(permission);
        Set<ConstraintViolation<Permission>> permissionViolations = validator.validate(permission);
        Set<ConstraintViolation<Role>> roleViolations = validator.validate(role);
        assertTrue(permissionViolations.isEmpty(), "Expected no violations for Permission object after adding Role");
        assertTrue(roleViolations.isEmpty(), "Expected no violations for Role object after adding Permission");
        assertTrue(permission.getRoles().contains(role));
        assertTrue(role.getPermissions().contains(permission), "Bidirectional relationship not maintained");
    }

    @Test
    void permissionNameShouldNotBeTooLong() {
        Permission permission = new Permission();
        permission.setName("A".repeat(256));
        permission.setDescription("Valid Description");
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);
        assertFalse(violations.isEmpty(), "Expected violations for name length exceeding 255 characters");
    }

    @Test
    void permissionDescriptionShouldNotBeTooLong() {
        Permission permission = new Permission();
        permission.setName("Valid Name");
        permission.setDescription("A".repeat(256));
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);
        assertFalse(violations.isEmpty(), "Expected violations for description length exceeding 255 characters");
    }
}