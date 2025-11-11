/* Hashmap extension */
CREATE EXTENSION IF NOT EXISTS pgcrypto;

/* Resets our tables by default */
TRUNCATE TABLE
users,
roles
RESTART IDENTITY CASCADE;

INSERT INTO roles (name) VALUES
('sale'),
('admin');

INSERT INTO users (username, password_hash, role_id) VALUES
('admin', crypt('admin', gen_salt('bf')), 2),
('customer', crypt('customer', gen_salt('bf')), 1)