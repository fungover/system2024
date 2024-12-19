package org.fungover.system2024.entities;
import jakarta.validation.*;
import org.fungover.system2024.user.entity.Role;
import org.fungover.system2024.user.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

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
        assertFalse(violations.isEmpty());
    }

    @Test
    void roleNameShouldHaveMaxLength() {
        Role role = new Role();
        role.setName("A".repeat(255));
        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertTrue(violations.isEmpty(), "Expected no violations for valid name length");

        role = new Role();
        role.setName("A".repeat(256));
        violations = validator.validate(role);
        assertFalse(violations.isEmpty(), "Expected violations for name length exceeding 255 characters");
    }

    @Test
    void roleUsersShouldBeEmptyInitially() {
        Role role = new Role();
        role.setName("ValidName");
        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertTrue(violations.isEmpty(), "Expected no violations for a new Role object");
       assertTrue(role.getUsers().isEmpty(),"Expected users to be empty for a new Role object");
    }

    @Test
    void rolePermissionsShouldBeEmptyInitially() {
        Role role = new Role();
        role.setName("ValidName");
        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertTrue(violations.isEmpty(), "Expected no violations for a new Role object");
        assertTrue(role.getPermissions().isEmpty(),"Expected permissions to be empty for a new Role object");
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


}