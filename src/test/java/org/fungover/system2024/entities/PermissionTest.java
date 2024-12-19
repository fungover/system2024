package org.fungover.system2024.entities;

import org.fungover.system2024.user.entity.Permission;
import org.fungover.system2024.user.entity.Role;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PermissionTests {

    private static Validator validator;
    private static ValidatorFactory factory;

    @BeforeAll
    static void setUpValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }

    @Test
    void permissionNameShouldNotBeNull() {
        Permission permission = new Permission();
        permission.setName("Read Access");
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);
        assertTrue(violations.stream()
                        .noneMatch(v -> v.getPropertyPath().toString().equals("name")),
                "Expected no violations for valid name");
        assertNotNull(permission.getName());

        permission.setName(null);
        violations = validator.validate(permission);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("name")),
                "Expected violation for null name");
    }

    @Test
    void permissionDescriptionShouldNotBeNull() {
        Permission permission = new Permission();
        permission.setDescription("Allows read access to resources");
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);
        assertTrue(violations.stream()
                        .noneMatch(v -> v.getPropertyPath().toString().equals("description")),
                "Expected no violations for valid description");
        assertNotNull(permission.getDescription());

        permission.setDescription(null);
        violations = validator.validate(permission);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("description")),
                "Expected violation for null description");
    }

    @Test
    void permissionNameShouldHaveMaxLength() {
        Permission permission = new Permission();
        permission.setName("A".repeat(255));
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);
        assertTrue(violations.stream()
                        .noneMatch(v -> v.getPropertyPath().toString().equals("name")),
                "Expected no violations for valid name length");

        permission.setName("A".repeat(256));
        violations = validator.validate(permission);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("name")),
                "Expected violations for name length exceeding 255 characters");
    }

    @Test
    void permissionDescriptionShouldHaveMaxLength() {
        Permission permission = new Permission();
        permission.setDescription("A".repeat(255));
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);
        assertTrue(violations.stream()
                        .noneMatch(v -> v.getPropertyPath().toString().equals("description")),
                "Expected no violations for valid description length");

        permission.setDescription("A".repeat(256));
        violations = validator.validate(permission);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("description")),
                "Expected violations for description length exceeding 255 characters");
    }

    @Test
    void permissionRolesShouldBeEmptyInitially() {
        Permission permission = new Permission();
        permission.setName("ValidName");
        permission.setDescription("ValidDescription");
        Set<ConstraintViolation<Permission>> violations = validator.validate(permission);
        assertTrue(violations.isEmpty(), "Expected no violations for a new Permission object");
        assertTrue(permission.getRoles().isEmpty(), "Expected roles to be empty for a new Permission object");
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
    void removeRoleFromPermission() {
        Permission permission = new Permission();
        permission.setName("ValidName");
        permission.setDescription("ValidDescription");
        Role role = new Role();
        role.setName("ValidRoleName");

        permission.getRoles().add(role);
        role.getPermissions().add(permission);

        permission.getRoles().remove(role);
        role.getPermissions().remove(permission);

        assertFalse(permission.getRoles().contains(role), "Role should be removed from Permission");
        assertFalse(role.getPermissions().contains(permission), "Permission should be removed from Role");
    }
}