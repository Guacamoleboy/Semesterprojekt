/* Hashmap extension */
CREATE EXTENSION IF NOT EXISTS pgcrypto;

/* Resets our tables by default */
TRUNCATE TABLE
users,
roles,
products
RESTART IDENTITY CASCADE;

INSERT INTO roles (name) VALUES
('sale'),
('admin')

INSERT INTO users (username, password_hash, role_id) VALUES
('employee', crypt('sale', gen_salt('bf')), 1),
('admin', crypt('admin', gen_salt('bf')), 2)

INSERT INTO products (title, description, size, quantity, price) VALUES
('Skrue1', 'Rustfri skrue', '100cm', 100, 5.50),
('Skrue2', 'Sort skrue', '50cm', 150, 4.00),
('Skrue3', 'Galvaniseret skrue', '50cm', 200, 6.20),
('Skrue3', 'Galvaniseret skrue', '100cm', 50, 8.00),
('Møtrik1', 'Rustfri møtrik', 'M5', 300, 1.50),
('Møtrik2', 'Sort møtrik', 'M6', 200, 2.00),
('Bolt1', 'Rustfri bolt', '10cm', 50, 7.00),
('Bolt2', 'Sort bolt', '15cm', 100, 9.00),
('Plade1', 'Metalplade', '2x2m', 20, 50.00),
('Plade2', 'Metalplade', '1x1m', 40, 25.00),
('Skive1', 'Rustfri skive', 'M5', 300, 0.50),
('Skive2', 'Sort skive', 'M6', 200, 0.80),
('Hjul1', 'Plastik hjul', '10cm', 50, 15.00),
('Hjul2', 'Gummi hjul', '15cm', 40, 20.00),
('Bøjle1', 'Metal bøjle', '50cm', 100, 12.00),
('Bøjle2', 'Rustfri bøjle', '100cm', 60, 18.00),
('Rør1', 'Stålrør', '1m', 30, 22.00),
('Rør2', 'Aluminium rør', '2m', 25, 28.00),
('Skruetrækker', 'Torx skruetrækker', '20cm', 150, 10.00),
('Hammer', 'Stålhammer', '35cm', 80, 15.00);