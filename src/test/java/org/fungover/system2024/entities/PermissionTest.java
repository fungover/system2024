package org.fungover.system2024.entities;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.security.Permission;
import java.util.Set;
class PermissionTest {
    private Permission permission;
    @BeforeEach
    void setUp() {
        permission = new Permission();
    }
    @Test
    void testPermissionInstatiation() {
        assertNotNull(permission);
    }
    @Test
    void testSetAndGetId() {
        permission.setId(1);
        assertEquals(1, permission.getId());
    }
    @Test
    void testSetAndGetName() {
        // Testa getter och setter för name
        permission.setName("Admin Access");
        assertEquals("Admin Access", permission.getName());
    }
    @Test
    void testSetAndGetDescription() {
        // Testa getter och setter för description
        permission.setDescription("Access to all admin functionalities");
        assertEquals("Access to all admin functionalities", permission.getDescription());
    }
}