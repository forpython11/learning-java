DROP TABLE IF EXISTS products;

CREATE TABLE products (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price > 0)
);
