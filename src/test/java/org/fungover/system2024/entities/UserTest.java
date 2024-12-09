package org.fungover.system2024.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

 class UserTest {

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
    void userIdShouldBeSetCorrectly() {
        user.setId(1);
        assertEquals(1, user.getId());
    }
}