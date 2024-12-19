package org.fungover.system2024.entities;

import jakarta.validation.*;
import org.fungover.system2024.user.entity.Role;
import org.fungover.system2024.user.entity.User;
import org.fungover.system2024.user.entity.Permission;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("name")),
                "Expected violations for null name");

        role.setName("ValidName");
        violations = validator.validate(role);
        assertTrue(violations.stream()
                        .noneMatch(v -> v.getPropertyPath().toString().equals("name")),
                "Expected no violations for valid name");
    }

    @Test
    void roleNameShouldHaveMaxLength() {
        Role role = new Role();
        role.setName("A".repeat(255));
        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertTrue(violations.stream()
                        .noneMatch(v -> v.getPropertyPath().toString().equals("name")),
                "Expected no violations for valid name length");

        role.setName("A".repeat(256));
        violations = validator.validate(role);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("name")),
                "Expected violations for name length exceeding 255 characters");
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

    @Test
    void addUserToRole() {
        Role role = new Role();
        role.setName("ValidName");
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("ValidLastName");
        user.setEmail("valid.email@gmail.com");
        user.setPassword("ValidPassword");
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
    void addPermissionToRole() {
        Role role = new Role();
        role.setName("ValidName");

        Permission permission = new Permission();
        permission.setName("ValidPermission");
        permission.setDescription("ValidDescription");

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
    void removePermissionFromRole() {
        Role role = new Role();
        role.setName("ValidName");
        Permission permission = new Permission();
        permission.setName("ValidPermission");
        role.getPermissions().add(permission);
        permission.getRoles().add(role);
        role.getPermissions().remove(permission);
        permission.getRoles().remove(role);
        assertFalse(role.getPermissions().contains(permission), "Permission should be removed from Role");
        assertFalse(permission.getRoles().contains(role), "Role should be removed from Permission");
    }
}