DROP TABLE file;
CREATE TABLE IF NOT EXISTS file(
    id INT AUTO_INCREMENT
        PRIMARY KEY,
    owner INT
            DEFAULT 000,
    name VARCHAR(255)
        CHARACTER SET utf8mb4
            COLLATE utf8mb4_unicode_ci NOT NULL,
    stored_filename VARCHAR(255)
        CHARACTER SET utf8mb4
            COLLATE utf8mb4_unicode_ci NOT NULL,
    soft_delete BOOLEAN DEFAULT 0

);