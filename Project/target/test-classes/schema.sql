DROP TABLE IF EXISTS
customers,
order_materials,
orders,
carport_orders,
carport_category,
materials,
materials_category,
users,
roles,
products
CASCADE;

CREATE TABLE roles (
id SERIAL PRIMARY KEY,
name VARCHAR(50) UNIQUE NOT NULL /* sale & admin */
);

CREATE TABLE users (
id SERIAL PRIMARY KEY,
username VARCHAR(100) UNIQUE NOT NULL,
password_hash TEXT NOT NULL,
role_id INT NOT NULL REFERENCES roles(id) ON DELETE RESTRICT,
created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE customers (
id SERIAL PRIMARY KEY,
firstname VARCHAR(100) NOT NULL,
lastname VARCHAR(100) NOT NULL,
email VARCHAR(255) UNIQUE NOT NULL,
phone VARCHAR(50) UNIQUE NOT NULL,
street VARCHAR(255),
city VARCHAR(100),
zipcode VARCHAR(20),
country VARCHAR(100),
created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE products (
id SERIAL PRIMARY KEY,
title VARCHAR(100) NOT NULL,
description VARCHAR(255),
size VARCHAR(50),
quantity INT NOT NULL,
price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE materials_category (
id SERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL UNIQUE /* Træ & Tagplader | Beslag & Skruer */
);

CREATE TABLE materials (
id SERIAL PRIMARY KEY,
category_id INT NOT NULL REFERENCES materials_category(id) ON DELETE RESTRICT,
name VARCHAR(150) NOT NULL,
description TEXT,
unit VARCHAR(20) NOT NULL, /* stk, pakke, rulle, sæt */
length DECIMAL(10, 2),
width DECIMAL(10, 2),
height DECIMAL(10, 2),
price DECIMAL(10, 2) NOT NULL /* Dynamic price */
);

CREATE TABLE carport_category (
id SERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE carport_orders (
id SERIAL PRIMARY KEY,
user_id INT REFERENCES users(id) ON DELETE SET NULL,
carport_category_id INT NOT NULL REFERENCES carport_category(id) ON DELETE RESTRICT,
width DECIMAL(10, 2) NOT NULL,
length DECIMAL(10, 2) NOT NULL,
height DECIMAL(10, 2) NOT NULL,
angle DECIMAL(10, 2),
roof VARCHAR(100), /* Fladt tag || Rejsning */
has_tool_shed BOOLEAN DEFAULT FALSE,
tool_shed_width DECIMAL(10, 2),
tool_shed_length DECIMAL(10, 2),
has_trapez BOOLEAN DEFAULT FALSE,
created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE orders (
id SERIAL PRIMARY KEY,
user_id INT REFERENCES users(id) ON DELETE SET NULL,
carport_order_id INT NOT NULL REFERENCES carport_orders(id) ON DELETE CASCADE,
total_price DECIMAL(10, 2) NOT NULL,
status VARCHAR(50) DEFAULT 'pending', /* Pending, Open, Closed */
created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE order_materials ( /* List of Materials for the specific carport order */
id SERIAL PRIMARY KEY,
order_id INT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
material_id INT NOT NULL REFERENCES materials(id) ON DELETE RESTRICT,
quantity DECIMAL(10, 2) NOT NULL,
unit_price DECIMAL(10, 2) NOT NULL, /* Price for the material at the time the order was placed | Static price */
total_price DECIMAL(10, 2) NOT NULL /* Quantity * unit_price */
);