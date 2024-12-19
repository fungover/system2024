package org.fungover.system2024.entities;

import jakarta.validation.*;
import org.fungover.system2024.user.entity.Role;
import org.fungover.system2024.user.entity.User;
import org.fungover.system2024.user.entity.Permission;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

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
    void roleNameShouldNotBeNull() {
        Role role = new Role();
        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        Optional<ConstraintViolation<Role>> violation = violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("name"))
                .findFirst();
        assertTrue(violation.isPresent(), "Expected violations for null name");
        assertEquals("must not be null", violation.get().getMessage(),
                "Unexpected validation message");

        role.setName("ValidName");
        violations = validator.validate(role);
        assertTrue(violations.stream()
                        .noneMatch(v -> v.getPropertyPath().toString().equals("name")),
                "Expected no violations for valid name");
    }

    @Test
    void roleNameShouldHaveMaxLength() {
        Role role = new Role();
        // Test empty string
        role.setName("");
        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertTrue(violations.isEmpty(), "Empty string should be valid");

        // Test boundary value
        role.setName("A".repeat(254));
        violations = validator.validate(role);
        assertTrue(violations.isEmpty(), "254 characters should be valid");

        role.setName("A".repeat(255));
        violations = validator.validate(role);
        assertTrue(violations.stream()
                        .noneMatch(v -> v.getPropertyPath().toString().equals("name")),
                "Expected no violations for valid name length");
    }

    @Test
    void roleUsersShouldBeEmptyInitially() {
        Role role = new Role();
        role.setName("ValidName");
        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertTrue(violations.isEmpty(), "Expected no violations for a new Role object");
        assertTrue(role.getUsers().isEmpty(), "Expected users to be empty for a new Role object");
    }

    @Test
    void rolePermissionsShouldBeEmptyInitially() {
        Role role = new Role();
        role.setName("ValidName");
        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertTrue(violations.isEmpty(), "Expected no violations for a new Role object");
        assertTrue(role.getPermissions().isEmpty(), "Expected permissions to be empty for a new Role object");
    }

    private User createValidUser() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("ValidLastName");
        user.setEmail("valid.email@gmail.com");
        user.setPassword("ValidPassword");
        return user;
    }

    @Test
    void addUserToRole() {
        Role role = new Role();
        role.setName("ValidName");
        User user = createValidUser();
        role.getUsers().add(user);
        user.getRoles().add(role);
        Set<ConstraintViolation<Role>> roleViolations = validator.validate(role);
        Set<ConstraintViolation<User>> userViolations = validator.validate(user);
        assertTrue(roleViolations.isEmpty(), "Expected no violations for Role object after adding User");
        assertTrue(userViolations.isEmpty(), "Expected no violations for User object after adding Role");
        assertTrue(role.getUsers().contains(user));
        assertTrue(user.getRoles().contains(role), "Bidirectional relationship not maintained");
    }

    @Test
    void addInvalidUserToRole() {
        Role role = new Role();
        role.setName("ValidName");
        User user = new User(); // Invalid user without required fields
        role.getUsers().add(user);
        user.getRoles().add(role);

        Set<ConstraintViolation<User>> userViolations = validator.validate(user);
        assertFalse(userViolations.isEmpty(), "Expected violations for invalid User");
    }

    private Permission createValidPermission() {
        Permission permission = new Permission();
        permission.setName("ValidPermission");
        permission.setDescription("ValidDescription");
        return permission;
    }

    @Test
    void addPermissionToRole() {
        Role role = new Role();
        role.setName("ValidName");
        Permission permission = createValidPermission();
        role.getPermissions().add(permission);
        permission.getRoles().add(role);
        Set<ConstraintViolation<Role>> roleViolations = validator.validate(role);
        Set<ConstraintViolation<Permission>> permissionViolations = validator.validate(permission);
        assertTrue(roleViolations.isEmpty(), "Expected no violations for Role object after adding Permission");
        assertTrue(permissionViolations.isEmpty(), "Expected no violations for Permission object after adding Role");
        assertTrue(role.getPermissions().contains(permission));
        assertTrue(permission.getRoles().contains(role), "Bidirectional relationship not maintained");
    }

    @Test
    void addInvalidPermissionToRole() {
        Role role = new Role();
        role.setName("ValidName");
        Permission permission = new Permission(); // Invalid permission without required fields
        role.getPermissions().add(permission);
        permission.getRoles().add(role);

        Set<ConstraintViolation<Permission>> permissionViolations = validator.validate(permission);
        assertFalse(permissionViolations.isEmpty(), "Expected violations for invalid Permission");
    }

    @Test
    void removePermissionFromRole() {
        Role role = new Role();
        role.setName("ValidName");
        Permission permission = createValidPermission();
        role.getPermissions().add(permission);
        permission.getRoles().add(role);
        role.getPermissions().remove(permission);
        permission.getRoles().remove(role);

        Set<ConstraintViolation<Role>> roleViolations = validator.validate(role);
        Set<ConstraintViolation<Permission>> permissionViolations = validator.validate(permission);
        assertTrue(roleViolations.isEmpty(), "Role should remain valid after removing Permission");
        assertTrue(permissionViolations.isEmpty(), "Permission should remain valid after removing Role");
        assertFalse(role.getPermissions().contains(permission), "Permission should be removed from Role");
        assertFalse(permission.getRoles().contains(role), "Role should be removed from Permission");
    }

    @Test
    void removeUserFromRole() {
        Role role = new Role();
        role.setName("ValidName");
        User user = createValidUser();
        role.getUsers().add(user);
        user.getRoles().add(role);
        role.getUsers().remove(user);
        user.getRoles().remove(role);

        Set<ConstraintViolation<Role>> roleViolations = validator.validate(role);
        Set<ConstraintViolation<User>> userViolations = validator.validate(user);
        assertTrue(roleViolations.isEmpty(), "Role should remain valid after removing User");
        assertTrue(userViolations.isEmpty(), "User should remain valid after removing Role");
        assertFalse(role.getUsers().contains(user), "User should be removed from Role");
        assertFalse(user.getRoles().contains(role), "Role should be removed from User");
    }
}