CREATE TABLE IF NOT EXISTS user
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
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