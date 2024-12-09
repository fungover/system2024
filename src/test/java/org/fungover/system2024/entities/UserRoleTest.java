package org.fungover.system2024.entities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import javax.management.relation.Role;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class UserRoleTest {
    private UserRole userRole;
    private User mockUser;
    private Role mockRole;
    private UserRoleId mockUserRoleId;
    @BeforeEach
    void setUp() {
        // Create a mock object of User and Role
        mockUser = Mockito.mock(User.class);
        mockRole = Mockito.mock(Role.class);
        // Create a mock-id for UserRole
        mockUserRoleId = Mockito.mock(UserRoleId.class);
        // Create a UserRole object
        userRole = new UserRole();
        userRole.setId(mockUserRoleId);
        userRole.setUser(mockUser);
        userRole.setRole(mockRole);
    }
    @Test
    void testUserRoleInstantiation() {
        // Check if UserRole object is created
        assertNotNull(userRole);
    }
    @Test
    void testUserRoleId() {
        // Checks if the userRoleId is set correctly
        assertEquals(mockUserRoleId, userRole.getId());
    }
    @Test
    void testUserRoleUser() {
        // Checks if the user is set correctly
        assertEquals(mockUser, userRole.getUser());
    }
    @Test
    void testUserRoleRole() {
        // Checks if the role is set correctly
        assertEquals(mockRole, userRole.getRole());
    }
    @Test
    void testMapsId() {
        // Checks if the id is mapped correctly
        when(mockUserRoleId.getRoleId()).thenReturn(1);
        when(mockUserRoleId.getUserId()).thenReturn(2);
        assertEquals(1, mockUserRoleId.getRoleId());
        assertEquals(2, mockUserRoleId.getUserId());
    }
}