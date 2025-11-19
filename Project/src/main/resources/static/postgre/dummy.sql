-- Hashmap extension
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Resets our tables by default
TRUNCATE TABLE
customers,
carport_category,
materials,
materials_category,
users,
roles,
products
RESTART IDENTITY CASCADE;

INSERT INTO roles (name) VALUES
('sale'),
('admin');

-- Users using our BCrypt extension
INSERT INTO users (username, password_hash, role_id) VALUES
('employee', crypt('sale', gen_salt('bf')), 1),
('admin', crypt('admin', gen_salt('bf')), 2);

INSERT INTO customers (firstname, lastname, email, phone, street, city, zipcode, country) VALUES
('Mads', 'Kristensen', 'mads.kristensen@example.com', '+4522334455', 'Nørrebrogade 102', 'København', '2200', 'Denmark'),
('Sara', 'Lund', 'sara.lund@example.com', '+4520118899', 'Østerbrogade 45', 'København', '2100', 'Denmark'),
('Jonas', 'Hansen', 'jonas.hansen@example.com', '+4544556677', 'Vestergade 12', 'Aarhus', '8000', 'Denmark'),
('Line', 'Poulsen', 'line.poulsen@example.com', '+4533667788', 'Hovedvejen 77', 'Roskilde', '4000', 'Denmark'),
('Emil', 'Jørgensen', 'emil.jorgensen@example.com', '+4544221133', 'Algade 5', 'Aalborg', '9000', 'Denmark');

-- Needed? Imo it's "materials".
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

INSERT INTO materials_category (name) VALUES
('Træ & Tagplader'),
('Beslag & Skruer');

INSERT INTO materials (category_id, name, description, unit, length, width, height, price) VALUES
-- Træ & Tagplader
(1, 'Brædt 360', '25x200 mm trykimp. understernbrædder til for & bag ende', 'stk', 360, 25, 200, 20.00),
(1, 'Brædt 540', '25x200 mm trykimp. understernbrædder til siderne', 'stk', 540, 25, 200, 25.00),
(1, 'Overstern 360', '25x125 mm trykimp. oversternbrædder til forenden', 'stk', 360, 25, 125, 15.00),
(1, 'Overstern 540', '25x125 mm trykimp. oversternbrædder til siderne', 'stk', 540, 25, 125, 18.00),
(1, 'Lægte 420', '38x73 mm ubh. til bagside af dør', 'stk', 420, 38, 73, 5.00),
(1, 'Reglar 270', '45x95 mm ub. løsholter til skur gavle', 'stk', 270, 45, 95, 3.00),
(1, 'Reglar 240', '45x95 mm ub. løsholter til skur sider', 'stk', 240, 45, 95, 2.50),
(1, 'Spær 600', '45x195 mm spærtræ ubh. remme i sider, sadles ned i stolper', 'stk', 600, 45, 195, 10.00),
(1, 'Spær 480', '45x195 mm spærtræ ubh. remme i sider, sadles ned i stolper (skur del, deles)', 'stk', 480, 45, 195, 8.00),
(1, 'Spær 600', '45x195 mm spærtræ ubh. 15 stk spær, monteres på rem', 'stk', 600, 45, 195, 12.00),
(1, 'Stolpe 300', '97x97 mm trykimp. Stolper nedgraves 90 cm i jord', 'stk', 300, 97, 97, 15.00),
(1, 'Brædt 210', '19x100 mm trykimp. beklædning af skur 1 på 2', 'stk', 210, 19, 100, 1.50),
(1, 'Vandbrædt 540', '19x100 mm trykimp. på stern i sider', 'stk', 540, 19, 100, 3.00),
(1, 'Vandbrædt 360', '19x100 mm trykimp. på stern i forende', 'stk', 360, 19, 100, 2.50),
(1, 'Plastmo Ecolite 600', 'Blåtonet tagplade, monteres på spær', 'stk', 600, NULL, NULL, 50.00),
(1, 'Plastmo Ecolite 360', 'Blåtonet tagplade, monteres på spær', 'stk', 360, NULL, NULL, 35.00),
-- Beslag & Skruer
(2, 'Bundskruer 200', 'Plastmo, 3 pakke, til tagplader', 'pakke', 200, NULL, NULL, 0.50),
(2, 'Hulbånd 1x20mm', '10 mtr., 2 rulle, til vindkryds på spær', 'rulle', 1000, 20, NULL, 2.50),
(2, 'Universal skruer 190mm højre', '15 stk til montering af spær på rem', 'stk', 190, NULL, NULL, 1.50),
(2, 'Universal skruer 190mm venstre', '15 stk til montering af spær på rem', 'stk', 190, NULL, NULL, 1.50),
(2, 'Skruer 4,5x60mm 200stk', '1 pakke til montering af stern & vandbrædt', 'pakke', 60, 4.5, NULL, 0.10),
(2, 'Beslagskruer 4,0x50mm 250stk', '3 pakke til montering af universalbeslag + hulbånd', 'pakke', 50, 4, NULL, 0.08),
(2, 'Bræddebolt 10x120mm', '18 stk til montering af rem på stolper', 'stk', 120, 10, NULL, 0.75),
(2, 'Firkantskiver 40x40x11mm', '12 stk til montering af rem på stolper', 'stk', 40, 40, 11, 0.25),
(2, 'Skruer 4,5x70mm 400stk', '2 pakke til montering af yderste beklædning', 'pakke', 70, 4.5, NULL, 0.12),
(2, 'Skruer 4,5x50mm 300stk', '2 pakke til montering af inderste beklædning', 'pakke', 50, 4.5, NULL, 0.10),
(2, 'Stalddørsgreb 50x75', '1 sæt til lås på dør i skur', 'sæt', 50, 75, NULL, 5.00),
(2, 'Hængsel 390mm', '2 stk til skurdør', 'stk', 390, NULL, NULL, 3.50),
(2, 'Vinkelbeslag 35', '32 stk til montering af løsholter i skur', 'stk', 35, NULL, NULL, 0.20);

INSERT INTO carport_category (name) VALUES
('Fladt tag'),
('Høj rejsning');