/* Hashmap extension */
CREATE EXTENSION IF NOT EXISTS pgcrypto;

/* Resets our tables by default */
TRUNCATE TABLE
users,
roles
RESTART IDENTITY CASCADE;

INSERT INTO roles (name) VALUES
('employee'),
('admin'),
('owner');

INSERT INTO users (username, password_hash, role_id) VALUES
('employee', crypt('employee', gen_salt('bf')), 1),
('admin', crypt('admin', gen_salt('bf')), 2),
('owner', crypt('owner', gen_salt('bf')), 3);