CREATE TABLE IF NOT EXISTS file(
    id INT AUTO_INCREMENT
        PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    file_url TEXT NOT NULL
)