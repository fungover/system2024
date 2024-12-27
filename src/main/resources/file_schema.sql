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

# CREATE TABLE file (
#                       id INT AUTO_INCREMENT PRIMARY KEY,
#                       owner INT,
#                       original_filename VARCHAR(255) NOT NULL,
#                       stored_filename VARCHAR(255) NOT NULL,
#                       soft_delete BOOLEAN DEFAULT FALSE,
#                       CONSTRAINT fk_owner_id FOREIGN KEY (owner) REFERENCES user(id)
# );