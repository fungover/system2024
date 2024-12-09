package org.fungover.system2024.entitites;

import org.fungover.system2024.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void userNameShouldNotBeNull() {
        user.setName("John Doe");
        assertNotNull(user.getName());
    }

    @Test
    void userEmailShouldNotBeNull() {
        user.setEmail("john.doe@example.com");
        assertNotNull(user.getEmail());
    }

    @Test
    void userPasswordShouldNotBeNull() {
        user.setPassword("securepassword");
        assertNotNull(user.getPassword());
    }

    @Test
    void userNameShouldNotExceedMaxLength() {
        user.setName("a".repeat(256));
        assertTrue(user.getName().length() <= 255);
    }

    @Test
    void userEmailShouldNotExceedMaxLength() {
        user.setEmail("a".repeat(256));
        assertTrue(user.getEmail().length() <= 255);
    }

    @Test
    void userPasswordShouldNotExceedMaxLength() {
        user.setPassword("a".repeat(256));
        assertTrue(user.getPassword().length() <= 255);
    }

    @Test
    void userIdShouldBeSetCorrectly() {
        user.setId(1);
        assertEquals(1, user.getId());
    }
}