package org.fungover.system2024.entitites;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RolePermissionIdTest {

    @Test
    public void testEqualsAndHashCode() {
        RolePermissionId rolePermissionId1 = new RolePermissionId();
        rolePermissionId1.setRoleId(1);
        rolePermissionId1.setPermissionId(2);

        RolePermissionId rolePermissionId2 = new RolePermissionId();
        rolePermissionId2.setRoleId(1);
        rolePermissionId2.setPermissionId(2);

        RolePermissionId rolePermissionId2 = new RolePermissionId();
        rolePermissionId2.setRoleId(2);
        rolePermissionId2.setPermissionId(3);

        //Testing if two equal objects are equal
        assertEquals(rolePermissionId1, rolePermissionId2);
        assertEquals(rolePermissionId1.hashCode(), rolePermissionId2.hashCode());

        //Testing if two different objects are not equal
        assertNotEquals(rolePermissionId1, rolePermissionId3);
    }

    @Test
    public void testConstructorAndGetterSetter() {
        RolePermissionId rolePermissionId = new RolePermissionId();
        rolePermissionId.setPermissionId(1);
        rolePermissionId.setRoleId(2);

        //Testing if the constructor and the getter and setter methods work
        assertEquals(1, rolePermissionId.getPermissionId());
        assertEquals(2, rolePermissionId.getRoleId());
    }
}
