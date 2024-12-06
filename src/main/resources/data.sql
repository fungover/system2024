SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE role;
TRUNCATE TABLE permission;
TRUNCATE TABLE user;
TRUNCATE TABLE user_role;
TRUNCATE TABLE role_permission;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO role (id, name)
VALUES (1, 'Admin'),
       (2, 'Editor'),
       (3, 'Viewer');

INSERT INTO permission (id, name, description)
VALUES (1, 'GET', 'Permission to perform GET operations (read data)'),
       (2, 'POST', 'Permission to perform POST operations (create data)'),
       (3, 'PATCH', 'Permission to perform PATCH operations (update part of data)'),
       (4, 'PUT', 'Permission to perform PUT operations (update full data)'),
       (5, 'DELETE', 'Permission to perform DELETE operations (delete data)');

INSERT INTO user (id, name, email, password)
VALUES (1, 'John Doe', 'john.doe@example.com', 'C@ndyFl0$$R0cket_ExTr3me!42'),
       (2, 'Jane Smith', 'jane.smith@example.com', 'Unic0rn$ushi-Slam@247%'),
       (3, 'Alice Johnson', 'alice.johnson@example.com', 'BananaPancake_H@rdC0re777!');

INSERT INTO user_role (role_id, user_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);

INSERT INTO role_permission (permission_id, role_id)
VALUES (1, 3),
       (2, 2),
       (3, 2),
       (4, 2),
       (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1);
