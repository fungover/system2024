
CREATE TABLE IF NOT EXISTS user
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    first_name     VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    UNIQUE (email)
    );

CREATE TABLE IF NOT EXISTS role
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_role
(
    role_id INT,
    user_id INT,
    PRIMARY KEY (role_id, user_id),
    FOREIGN KEY (role_id) REFERENCES role (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE IF NOT EXISTS permission
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT

);

CREATE TABLE IF NOT EXISTS role_permission
(
    permission_id INT,
    role_id       INT,
    PRIMARY KEY (permission_id, role_id),
    FOREIGN KEY (permission_id) REFERENCES permission (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);

DROP TABLE IF EXISTS file;
CREATE TABLE IF NOT EXISTS file(
                                   id INT AUTO_INCREMENT
                                       PRIMARY KEY,
                                   owner INT,
                                   original_filename VARCHAR(255)
                                          CHARACTER SET utf8mb4
                                          COLLATE utf8mb4_unicode_ci NOT NULL,
                                   stored_filename VARCHAR(255)
                                          CHARACTER SET utf8mb4
                                          COLLATE utf8mb4_unicode_ci NOT NULL,
                                   soft_delete BOOLEAN DEFAULT 0,

                                   CONSTRAINT fk_owner_id FOREIGN KEY (owner) REFERENCES user(id)

);
