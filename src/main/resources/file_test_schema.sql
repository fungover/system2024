CREATE TABLE IF NOT EXISTS file(
    id INT AUTO_INCREMENT
        PRIMARY KEY,
    name VARCHAR(255)
        CHARACTER SET utf8mb4
            COLLATE utf8mb4_unicode_ci NOT NULL,
    file_url TEXT
        CHARACTER SET utf8mb4
            COLLATE utf8mb4_unicode_ci NOT NULL
);
