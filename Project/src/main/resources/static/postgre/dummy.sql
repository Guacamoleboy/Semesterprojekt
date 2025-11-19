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
('Træ'),
('Metal'),
('Plast/Polycarbonat'),
('Tagmateriale'),
('Gulv'),
('Vinduer'),
('Døre'),
('Skruer'),
('Beslag');


INSERT INTO materials (category_id, name, description, unit, length, width, height, price) VALUES
-- Træ
-- Spær, Rem og stolper

(1, 'Brædt 360', '25x200 mm trykimp. understernbrædder til for & bag ende', 'stk', 360, 25, 200, 20.00),
(1, 'Brædt 540', '25x200 mm trykimp. understernbrædder til siderne', 'stk', 540, 25, 200, 25.00),
(1, 'Overstern 360', '25x125 mm trykimp. oversternbrædder til forende', 'stk', 360, 25, 125, 15.00),
(1, 'Overstern 540', '25x125 mm trykimp. oversternbrædder til siderne', 'stk', 540, 25, 125, 18.00),
(1, 'Lægte 420', '38x73 mm ubh. til bagside af dør', 'stk', 420, 38, 73, 5.00),
(1, 'Lægte 600', '38x73 mm ubh. til tagstøtte', 'stk', 600, 38, 73, 7.00),
(1, 'Reglar 270', '45x95 mm ub. løsholter til skur gavle', 'stk', 270, 45, 95, 3.00),
(1, 'Reglar 240', '45x95 mm ub. løsholter til skur sider', 'stk', 240, 45, 95, 2.50),
(1, 'Spær 480', '45x195 mm ubh. spær til skur', 'stk', 480, 45, 195, 8.00),
(1, 'Spær 600', '45x195 mm ubh. spær til skur', 'stk', 600, 45, 195, 10.00),
(1, 'Spær 720', '45x195 mm ubh. spær til større skur', 'stk', 720, 45, 195, 12.00),
(1, 'Stolpe 300', '97x97 mm trykimp. stolper nedgraves 90 cm i jord', 'stk', 300, 97, 97, 15.00),
(1, 'Stolpe 400', '97x97 mm trykimp. stolper til højere skur', 'stk', 400, 97, 97, 20.00),
(1, 'Brædt 210', '19x100 mm trykimp. beklædning af skur', 'stk', 210, 19, 100, 1.50),
(1, 'Vandbrædt 540', '19x100 mm trykimp. på stern i sider', 'stk', 540, 19, 100, 3.00),
(1, 'Vandbrædt 360', '19x100 mm trykimp. på stern i forende', 'stk', 360, 19, 100, 2.50),

-- Metal
(2, 'Metalramme 2×3 m', 'Støttestruktur til skur 2x3 m', 'stk', 250, 5, 5, 300),
(2, 'Metalramme 3×4 m', 'Støttestruktur til skur 3x4 m', 'stk', 300, 5, 5, 400),

-- Plast/Polycarbonat
(3, 'Plastmo Ecolite 360', 'Blåtonet tagplade, monteres på spær', 'stk', 360, NULL, NULL, 35.00),
(3, 'Plastmo Ecolite 420', 'Blåtonet tagplade, monteres på spær', 'stk', 420, NULL, NULL, 40.00),
(3, 'Plastmo Ecolite 600', 'Blåtonet tagplade, monteres på spær', 'stk', 600, NULL, NULL, 50.00),

-- Tagmateriale
(4, 'Tagpap 2×3 m', 'Til fladt eller let skrånende tag', 'm2', 200, 100, 0, 50.00),
(4, 'Tagpap 3×4 m', 'Til fladt eller let skrånende tag', 'm2', 300, 150, 0, 80.00),
(4, 'Bølgeplast 2×3 m', 'Let tagmateriale', 'm2', 200, 100, 0, 70.00),
(4, 'Bølgeplast 3×4 m', 'Let tagmateriale', 'm2', 300, 150, 0, 100.00),

-- Gulv
(5, 'Træplade gulv 2×3 m', 'Valgfrit gulv til skur 2x3 m', 'stk', 200, 100, 2, 80.00),
(5, 'Træplade gulv 3×4 m', 'Valgfrit gulv til skur 3x4 m', 'stk', 300, 150, 2, 120.00),

-- Vinduer
(6, 'Polycarbonat-vindue 60x60 cm', 'Gennemsigtigt vindue', 'stk', 60, 60, 2, 120.00),
(6, 'Polycarbonat-vindue 80x80 cm', 'Gennemsigtigt vindue', 'stk', 80, 80, 2, 180.00),

-- Døre
(7, 'Trædør 90x200 cm', 'Standard skurdør', 'stk', 90, 200, 3, 250.00),
(7, 'Trædør 100x210 cm', 'Større skurdør', 'stk', 100, 210, 3, 300.00),

-- Skruer
(8, 'Skruer 4,5x50mm', 'Til inderste beklædning', 'pakke', 50, 4.5, NULL, 0.10),
(8, 'Skruer 4,5x70mm', 'Til yderste beklædning', 'pakke', 70, 4.5, NULL, 0.12),
(8, 'Skruer 5x50mm', 'Til tagplader', 'pakke', 50, 5, NULL, 0.50),
(8, 'Universal skruer 190mm højre', 'Til montering af spær på rem', 'stk', 190, NULL, NULL, 1.50),
(8, 'Universal skruer 190mm venstre', 'Til montering af spær på rem', 'stk', 190, NULL, NULL, 1.50),

-- Beslag
(9, 'Vinkelbeslag 35', 'Til montering af løsholter', 'stk', 35, NULL, NULL, 0.20),
(9, 'Hjørnebeslag', 'Forstærker hjørner i skurrammen', 'stk', 10, 10, 0.3, 20.00),
(9, 'Hængsel 390mm', 'Til skurdør', 'stk', 390, NULL, NULL, 3.50),
(9, 'Stalddørsgreb 50x75', 'Til lås på skurdør', 'sæt', 50, 75, NULL, 5.00);

INSERT INTO carport_category (name) VALUES
('Fladt tag'),
('Høj rejsning');