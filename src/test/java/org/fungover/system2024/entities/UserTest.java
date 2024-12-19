package org.fungover.system2024.entities;

import jakarta.validation.*;
import org.fungover.system2024.user.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void userNameShouldBeWithinMaxLength() {
        User user = new User();
        user.setFirst_name("A".repeat(255));
        user.setLast_name("ValidLastName");
        user.setEmail("valid.email@example.com");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Expected no violations for valid first name length");
    }

    @Test
    void userLastNameShouldBeWithinMaxLength() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("A".repeat(255));
        user.setEmail("valid.email@example.com");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Expected no violations for valid last name length");
    }

    @Test
    void userEmailShouldBeWithinMaxLength() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("ValidLastName");
        user.setEmail("a".repeat(91) + "@example.com");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Expected violations for email length exceeding 100 characters");
    }

    @Test
    void userPasswordShouldBeWithinMaxLength() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("ValidLastName");
        user.setEmail("valid.email@example.com");
        user.setPassword("A".repeat(60));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Expected no violations for valid password length");
    }

    @Test
    void userNameShouldNotExceedMaxLength() {
        User user = new User();
        user.setFirst_name("A".repeat(256));
        user.setLast_name("ValidLastName");
        user.setEmail("valid.email@example.com");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Expected violations for first name length exceeding 255 characters");
    }

    @Test
    void userLastNameShouldNotExceedMaxLength() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("A".repeat(256));
        user.setEmail("valid.email@example.com");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Expected violations for last name length exceeding 255 characters");
    }

    @Test
    void userEmailShouldNotExceedMaxLength() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("ValidLastName");
        user.setEmail("a".repeat(92) + "@example.com");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Expected violations for email length exceeding 100 characters");
    }

    @Test
    void userPasswordShouldNotExceedMaxLength() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("ValidLastName");
        user.setEmail("valid.email@example.com");
        user.setPassword("A".repeat(61));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Expected violations for password length exceeding 60 characters");
    }

    @Test
    void userNameShouldNotBeBlankOrNull() {
        User user = new User();
        user.setFirst_name("");
        user.setLast_name("ValidLastName");
        user.setEmail("valid.email@example.com");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Expected violations for blank first name");

        user.setFirst_name(null);
        violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Expected violations for null first name");
    }

    @Test
    void userLastNameShouldNotBeBlankOrNull() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("");
        user.setEmail("valid.email@example.com");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Expected violations for blank last name");

        user.setLast_name(null);
        violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Expected violations for null last name");
    }

    @Test
    void userEmailShouldNotBeBlankOrNull() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("ValidLastName");
        user.setEmail("");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Expected violations for blank email");

        user.setEmail(null);
        violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Expected violations for null email");
    }

    @Test
    void userPasswordShouldNotBeBlankOrNull() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("ValidLastName");
        user.setEmail("valid.email@example.com");
        user.setPassword("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Expected violations for blank password");

        user.setPassword(null);
        violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Expected violations for null password");
    }

    @Test
    void userEmailShouldBeValidFormat() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("ValidLastName");
        user.setEmail("invalid-email");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Expected violations for invalid email format");
    }
}