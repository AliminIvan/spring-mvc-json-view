drop table if exists order_products;
drop table if exists orders;
drop table if exists users;
CREATE TABLE users
(
    id    UUID PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE orders
(
    order_id      UUID PRIMARY KEY,
    amount  DECIMAL(19, 2) NOT NULL,
    status  VARCHAR(50)    NOT NULL,
    user_id UUID           NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE order_products
(
    order_id UUID         NOT NULL,
    product  VARCHAR(255) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (order_id)
);
