-- Hashmap extension
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Resets our tables by default
TRUNCATE TABLE
carport_orders,
orders,
customers,
carport_category,
materials,
materials_category,
users,
roles
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
('Jonas', 'Larsen', 'jonas68@live.dk', '+4520204175', 'Hovedvejen 77', 'Roskilde', '4000', 'Denmark'),
('Emil', 'Jørgensen', 'emil.jorgensen@example.com', '+4544221133', 'Algade 5', 'Aalborg', '9000', 'Denmark');

INSERT INTO materials_category (name) VALUES
('Træ & Tagplader'),
('Beslag & Skruer');

INSERT INTO materials (category_id, name, description, unit, length, width, height, price) VALUES
-- Træ & Tagplader
(1, 'Brædt 360', '25x200 mm trykimp. understernbrædder til for & bag ende', 'stk', 360, 25, 200, 151.00),
(1, 'Brædt 540', '25x200 mm trykimp. understernbrædder til siderne', 'stk', 540, 25, 200, 225.00),
(1, 'Overstern 360', '25x125 mm trykimp. oversternbrædder til forenden', 'stk', 360, 25, 125, 95.00),
(1, 'Overstern 540', '25x125 mm trykimp. oversternbrædder til siderne', 'stk', 540, 25, 125, 135.00),
(1, 'Lægte 420', '38x73 mm ubh. til bagside af dør', 'stk', 420, 38, 73, 35.00),
(1, 'Reglar 270', '45x95 mm ub. løsholter til skur gavle', 'stk', 270, 45, 95, 27.00),
(1, 'Reglar 240', '45x95 mm ub. løsholter til skur sider', 'stk', 240, 45, 95, 22.00),
(1, 'Spær 600', '45x195 mm spærtræ ubh. remme i sider, sadles ned i stolper', 'stk', 600, 45, 195, 310.00),
(1, 'Spær 480', '45x195 mm spærtræ ubh. remme i sider, sadles ned i stolper (skur del, deles)', 'stk', 480, 45, 195, 250.00),
(1, 'Spær 600', '45x195 mm spærtræ ubh. 15 stk spær, monteres på rem', 'stk', 600, 45, 195, 330.00),
(1, 'Stolpe 300', '97x97 mm trykimp. Stolper nedgraves 90 cm i jord', 'stk', 300, 97, 97, 190.00),
(1, 'Brædt 210', '19x100 mm trykimp. beklædning af skur 1 på 2', 'stk', 210, 19, 100, 40.00),
(1, 'Vandbrædt 540', '19x100 mm trykimp. på stern i sider', 'stk', 540, 19, 100, 65.00),
(1, 'Vandbrædt 360', '19x100 mm trykimp. på stern i forende', 'stk', 360, 19, 100, 50.00),
(1, 'Plastmo Ecolite 600', 'Blåtonet tagplade, monteres på spær', 'stk', 600, NULL, NULL, 240.00),
(1, 'Plastmo Ecolite 360', 'Blåtonet tagplade, monteres på spær', 'stk', 360, NULL, NULL, 160.00),
-- Beslag & Skruer
(2, 'Bundskruer 200', 'Plastmo, 3 pakke, til tagplader', 'pakke', 200, NULL, NULL, 120.00),
(2, 'Hulbånd 1x20mm', '10 mtr., 2 rulle, til vindkryds på spær', 'rulle', 1000, 20, NULL, 45.00),
(2, 'Universal skruer 190mm højre', '15 stk til montering af spær på rem', 'stk', 190, NULL, NULL, 12.00),
(2, 'Universal skruer 190mm venstre', '15 stk til montering af spær på rem', 'stk', 190, NULL, NULL, 12.00),
(2, 'Skruer 4,5x60mm 200stk', '1 pakke til montering af stern & vandbrædt', 'pakke', 60, 4.5, NULL, 75.00),
(2, 'Beslagskruer 4,0x50mm 250stk', '3 pakke til montering af universalbeslag + hulbånd', 'pakke', 50, 4, NULL, 80.00),
(2, 'Bræddebolt 10x120mm', '18 stk til montering af rem på stolper', 'stk', 120, 10, NULL, 25.00),
(2, 'Firkantskiver 40x40x11mm', '12 stk til montering af rem på stolper', 'stk', 40, 40, 11, 8.00),
(2, 'Skruer 4,5x70mm 400stk', '2 pakke til montering af yderste beklædning', 'pakke', 70, 4.5, NULL, 95.00),
(2, 'Skruer 4,5x50mm 300stk', '2 pakke til montering af inderste beklædning', 'pakke', 50, 4.5, NULL, 75.00),
(2, 'Stalddørsgreb 50x75', '1 sæt til lås på dør i skur', 'sæt', 50, 75, NULL, 150.00),
(2, 'Hængsel 390mm', '2 stk til skurdør', 'stk', 390, NULL, NULL, 65.00),
(2, 'Vinkelbeslag 35', '32 stk til montering af løsholter i skur', 'stk', 35, NULL, NULL, 5.00);

INSERT INTO carport_category (name) VALUES
('Fladt tag'),
('Høj rejsning');

INSERT INTO carport_orders (customer_id, carport_category_id, width, length, height, angle, roof, has_tool_shed, tool_shed_width, tool_shed_length, has_trapez) VALUES
(1, 1, 500.00, 600.00, 250.00, 15.00, 'Fladt tag', FALSE, NULL, NULL, FALSE),
(2, 2, 400.00, 500.00, 240.00, 20.00, 'Rejsning', TRUE, 150.00, 200.00, TRUE),
(5, 1, 450.00, 550.00, 245.00, 18.00, 'Fladt tag', FALSE, NULL, NULL, FALSE);

INSERT INTO orders (customer_id, carport_order_id, total_price, status) VALUES
(1, 1, 15000.00, 'offer'),
(2, 2, 22000.00, 'calculating'),
(1, 3, 30000.00, 'pending');