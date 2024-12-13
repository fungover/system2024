package org.fungover.system2024.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void userNameShouldBeWithinMaxLength() {
        User user = new User();
        user.setName("A".repeat(255));
        assertEquals(255, user.getName().length());
    }

    @Test
    void userEmailShouldBeWithinMaxLength() {
        User user = new User();
        user.setEmail("A".repeat(100));
        assertEquals(100, user.getEmail().length());
    }

    @Test
    void userPasswordShouldBeWithinMaxLength() {
        User user = new User();
        user.setPassword("A".repeat(60));
        assertEquals(60, user.getPassword().length());
    }
}