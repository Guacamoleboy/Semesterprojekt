/*

    Lavet af Gruppe D
    Semesterprojekt
    2. Semester

    Sidst opdateret af: Guacamoleboy
    Dato: 11/11-2025

*/

/* Resets */
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;

CREATE TABLE roles (
id SERIAL PRIMARY KEY,
name VARCHAR(50) UNIQUE NOT NULL /* sale & admin */
);

/* Users */
CREATE TABLE users (
id SERIAL PRIMARY KEY,
username VARCHAR(100) UNIQUE NOT NULL,
password_hash TEXT NOT NULL,
role_id INT NOT NULL REFERENCES roles(id) ON DELETE RESTRICT,
created_at TIMESTAMP DEFAULT NOW()
);

