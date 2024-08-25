INSERT INTO users (id, name, email)
VALUES ('ccda7651-18d9-4173-9a6f-00331664349e', 'John Doe', 'john.doe@example.com'),
       ('34b552ec-b040-4638-bd58-5842721015cb', 'Jane Smith', 'jane.smith@example.com');

INSERT INTO orders (order_id, amount, status, user_id)
VALUES ('8cb7a3f3-1a15-452a-8186-5a897f3563a0', 150.00, 'IN_PROGRESS', 'ccda7651-18d9-4173-9a6f-00331664349e'),
       ('2c84af7b-fc52-42a1-a98c-e2c575be663f', 250.00, 'COMPLETED', 'ccda7651-18d9-4173-9a6f-00331664349e'),
       ('53dae2a1-de67-453a-a1d0-127557269379', 75.50, 'IN_PROGRESS', '34b552ec-b040-4638-bd58-5842721015cb');

INSERT INTO order_products (order_id, product)
VALUES ('8cb7a3f3-1a15-452a-8186-5a897f3563a0', 'Laptop'),
       ('8cb7a3f3-1a15-452a-8186-5a897f3563a0', 'Smartphone'),
       ('2c84af7b-fc52-42a1-a98c-e2c575be663f', 'TV'),
       ('53dae2a1-de67-453a-a1d0-127557269379', 'PS5'),
       ('53dae2a1-de67-453a-a1d0-127557269379', 'Monitor');

