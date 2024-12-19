package org.fungover.system2024.entities;

import jakarta.validation.*;
import org.fungover.system2024.user.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

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

    private User createValidUser() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("ValidLastName");
        user.setEmail("valid.email@example.com");
        user.setPassword("ValidPassword123");
        return user;
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "user@domain.com",
            "user.name@domain.com",
            "user+tag@domain.com",
            "user@sub.domain.com"
    })
    void userEmailShouldAcceptValidFormats(String validEmail) {
        User user = createValidUser();
        user.setEmail(validEmail);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(),
                "Expected no violations for valid email: " + validEmail);
    }

    @Test
    void userNameShouldNotBeBlankOrNull() {
        User user = new User();
        user.setFirst_name("");
        user.setEmail("valid.email@example.com");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("first_name")),
                "Expected violations for blank first name");

        user.setFirst_name(null);
        violations = validator.validate(user);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("first_name")),
                "Expected violations for null first name");
    }

    @Test
    void userLastNameShouldNotBeBlankOrNull() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("");
        user.setEmail("valid.email@example.com");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("last_name")),
                "Expected violations for blank last name");

        user.setLast_name(null);
        violations = validator.validate(user);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("last_name")),
                "Expected violations for null last name");
    }

    @Test
    void userEmailShouldNotBeBlankOrNull() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("ValidLastName");
        user.setEmail("");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("email")),
                "Expected violations for blank email");

        user.setEmail(null);
        violations = validator.validate(user);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("email")),
                "Expected violations for null email");
    }

    @Test
    void userPasswordShouldNotBeBlankOrNull() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setEmail("valid.email@example.com");
        user.setPassword("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("password")),
                "Expected violations for blank password");

        user.setPassword(null);
        violations = validator.validate(user);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("password")),
                "Expected violations for null password");
    }

    @Test
    void userNameShouldBeWithinMaxLength() {
        User user = new User();
        user.setFirst_name("A".repeat(255));
        user.setLast_name("ValidLastName");
        user.setEmail("valid.email@example.com");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.stream()
                        .noneMatch(v -> v.getPropertyPath().toString().equals("first_name")),
                "Expected no violations for valid first name length");
    }

    @Test
    void userLastNameShouldBeWithinMaxLength() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("A".repeat(255));
        user.setEmail("valid.email@example.com");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.stream()
                        .noneMatch(v -> v.getPropertyPath().toString().equals("last_name")),
                "Expected no violations for valid last name length");
    }

    @Test
    void userEmailShouldBeWithinMaxLength() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("ValidLastName");
        user.setEmail("a".repeat(51) + "@example.com");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.stream()
                        .noneMatch(v -> v.getPropertyPath().toString().equals("email")),
                "Expected no violations for valid email length");
    }

    @Test
    void userPasswordShouldBeWithinMaxLength() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("ValidLastName");
        user.setEmail("valid.email@example.com");
        user.setPassword("A".repeat(60));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.stream()
                        .noneMatch(v -> v.getPropertyPath().toString().equals("password")),
                "Expected no violations for valid password length");
    }

    @Test
    void userNameShouldNotExceedMaxLength() {
        User user = new User();
        user.setFirst_name("A".repeat(256));
        user.setLast_name("ValidLastName");
        user.setEmail("valid.email@example.com");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("first_name")),
                "Expected violations for first name length exceeding 255 characters");
    }

    @Test
    void userLastNameShouldNotExceedMaxLength() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("A".repeat(256));
        user.setEmail("valid.email@example.com");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("last_name")),
                "Expected violations for last name length exceeding 255 characters");
    }

    @Test
    void userEmailShouldNotExceedMaxLength() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("ValidLastName");
        user.setEmail("a".repeat(92) + "@example.com");
        user.setPassword("ValidPassword123");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("email")),
                "Expected violations for email length exceeding 100 characters");
    }

    @Test
    void userPasswordShouldNotExceedMaxLength() {
        User user = new User();
        user.setFirst_name("ValidFirstName");
        user.setLast_name("ValidLastName");
        user.setEmail("valid.email@example.com");
        user.setPassword("A".repeat(61));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("password")),
                "Expected violations for password length exceeding 60 characters");
    }
}