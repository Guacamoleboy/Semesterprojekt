/*

    Lavet af Gruppe D
    Semesterprojekt
    2. Semester

    Sidst opdateret af: Rovelt123
    Dato: 12/11-2025

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

/* Products */
CREATE TABLE products (
id SERIAL PRIMARY KEY,
title VARCHAR(100) NOT NULL,
description VARCHAR(255),
size VARCHAR(50),
quantity INT NOT NULL,
price NUMERIC(10, 2) NOT NULL
);


