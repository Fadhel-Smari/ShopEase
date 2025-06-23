-- 🔄 Réinitialisation des données
DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM cart_items;
DELETE FROM products;
DELETE FROM categories;
DELETE FROM users;

-- 🌍 TABLE: users
INSERT INTO users (id, firstname, lastname, username, password, email, role) VALUES
(1, 'Admin', 'Root', 'admin', '$2a$10$fk7GakPqNE3rXqAuXNvgpuY05dM0I1k2vs1KLpwvwxhD0NjORHGU2', 'admin@example.com', 'ADMIN'),
(2, 'Ali', 'Benzarti', 'ali123', '$2a$10$NRm16gsPHqqqbHnQBGE5M.G0PsjUPSF7q5ekdkc6VArg68XJHY62K', 'ali@example.com', 'CLIENT'),
(3, 'Sara', 'Belkacem', 'sara456', '$2a$10$S2do0OT.Jl7.3zY43bP.e.kTWr2s2.zxAXJC8duniDFPj4w0phaSi', 'sara@example.com', 'CLIENT'),
(4, 'Mehdi', 'Ouimet', 'mehdi789', '$2a$10$JDyKDsFHZ8rAd6LhNJ6tvu6QNe3d9mvqmdIkLF0OtxaKhOj.xYGJW', 'mehdi@example.com', 'CLIENT');

-- 🗂️ TABLE: categories
INSERT INTO categories (id, name) VALUES
(1, 'Électronique'),
(2, 'Vêtements'),
(3, 'Maison'),
(4, 'Sport'),
(5, 'Beauté'),
(6, 'Livres');

-- 🛒 TABLE: products
INSERT INTO products (id, name, description, price, image_url, stock, category_id) VALUES
(1, 'Produit 1 - Électronique', 'Description du Produit 1 - Électronique', 15.52, 'https://via.placeholder.com/150?text=Produit+1+-+Électronique', 65, 1),
(2, 'Produit 2 - Électronique', 'Description du Produit 2 - Électronique', 67.79, 'https://via.placeholder.com/150?text=Produit+2+-+Électronique', 19, 1),
(3, 'Produit 3 - Électronique', 'Description du Produit 3 - Électronique', 137.05, 'https://via.placeholder.com/150?text=Produit+3+-+Électronique', 18, 1),
(4, 'Produit 4 - Électronique', 'Description du Produit 4 - Électronique', 26.94, 'https://via.placeholder.com/150?text=Produit+4+-+Électronique', 37, 1),
(5, 'Produit 5 - Vêtements', 'Description du Produit 5 - Vêtements', 36.73, 'https://via.placeholder.com/150?text=Produit+5+-+Vêtements', 48, 2),
(6, 'Produit 6 - Vêtements', 'Description du Produit 6 - Vêtements', 159.00, 'https://via.placeholder.com/150?text=Produit+6+-+Vêtements', 75, 2),
(7, 'Produit 7 - Vêtements', 'Description du Produit 7 - Vêtements', 149.59, 'https://via.placeholder.com/150?text=Produit+7+-+Vêtements', 32, 2),
(8, 'Produit 8 - Vêtements', 'Description du Produit 8 - Vêtements', 92.92, 'https://via.placeholder.com/150?text=Produit+8+-+Vêtements', 39, 2),
(9, 'Produit 9 - Maison', 'Description du Produit 9 - Maison', 33.72, 'https://via.placeholder.com/150?text=Produit+9+-+Maison', 34, 3),
(10, 'Produit 10 - Maison', 'Description du Produit 10 - Maison', 163.23, 'https://via.placeholder.com/150?text=Produit+10+-+Maison', 94, 3),
(11, 'Produit 11 - Maison', 'Description du Produit 11 - Maison', 109.80, 'https://via.placeholder.com/150?text=Produit+11+-+Maison', 81, 3),
(12, 'Produit 12 - Maison', 'Description du Produit 12 - Maison', 115.81, 'https://via.placeholder.com/150?text=Produit+12+-+Maison', 56, 3),
(13, 'Produit 13 - Sport', 'Description du Produit 13 - Sport', 183.76, 'https://via.placeholder.com/150?text=Produit+13+-+Sport', 92, 4),
(14, 'Produit 14 - Sport', 'Description du Produit 14 - Sport', 132.19, 'https://via.placeholder.com/150?text=Produit+14+-+Sport', 52, 4),
(15, 'Produit 15 - Sport', 'Description du Produit 15 - Sport', 95.64, 'https://via.placeholder.com/150?text=Produit+15+-+Sport', 87, 4),
(16, 'Produit 16 - Sport', 'Description du Produit 16 - Sport', 129.24, 'https://via.placeholder.com/150?text=Produit+16+-+Sport', 25, 4),
(17, 'Produit 17 - Beauté', 'Description du Produit 17 - Beauté', 170.99, 'https://via.placeholder.com/150?text=Produit+17+-+Beauté', 46, 5),
(18, 'Produit 18 - Beauté', 'Description du Produit 18 - Beauté', 137.02, 'https://via.placeholder.com/150?text=Produit+18+-+Beauté', 58, 5),
(19, 'Produit 19 - Beauté', 'Description du Produit 19 - Beauté', 35.78, 'https://via.placeholder.com/150?text=Produit+19+-+Beauté', 30, 5),
(20, 'Produit 20 - Beauté', 'Description du Produit 20 - Beauté', 197.28, 'https://via.placeholder.com/150?text=Produit+20+-+Beauté', 87, 5),
(21, 'Produit 21 - Livres', 'Description du Produit 21 - Livres', 177.11, 'https://via.placeholder.com/150?text=Produit+21+-+Livres', 77, 6),
(22, 'Produit 22 - Livres', 'Description du Produit 22 - Livres', 19.82, 'https://via.placeholder.com/150?text=Produit+22+-+Livres', 75, 6),
(23, 'Produit 23 - Livres', 'Description du Produit 23 - Livres', 10.81, 'https://via.placeholder.com/150?text=Produit+23+-+Livres', 80, 6),
(24, 'Produit 24 - Livres', 'Description du Produit 24 - Livres', 162.14, 'https://via.placeholder.com/150?text=Produit+24+-+Livres', 33, 6);

-- 🧺 TABLE: cart_items
INSERT INTO cart_items (id, user_id, product_id, quantity, total_price) VALUES
(1, 2, 5, 3, 110.19),
(2, 2, 18, 1, 137.02),
(3, 3, 17, 3, 512.97),
(4, 3, 8, 2, 185.84),
(5, 4, 12, 1, 115.81),
(6, 4, 14, 1, 132.19);

-- 🧾 TABLE: orders
INSERT INTO orders (id, user_id, order_date, status, total_amount) VALUES
(1, 2, NOW(), 'PENDING', 247.21),
(2, 3, NOW(), 'PENDING', 698.81),
(3, 4, NOW(), 'PENDING', 248.00);

-- 🧾 TABLE: order_items
INSERT INTO order_items (id, order_id, product_id, quantity, price) VALUES
(1, 1, 5, 3, 110.19),
(2, 1, 18, 1, 137.02),
(3, 2, 17, 3, 512.97),
(4, 2, 8, 2, 185.84),
(5, 3, 12, 1, 115.81),
(6, 3, 14, 1, 132.19);

-- 🔧 Réinitialisation des séquences après insertions manuelles
SELECT setval('users_id_seq',       (SELECT MAX(id) FROM users));
SELECT setval('categories_id_seq',  (SELECT MAX(id) FROM categories));
SELECT setval('products_id_seq',    (SELECT MAX(id) FROM products));
SELECT setval('cart_items_id_seq',  (SELECT MAX(id) FROM cart_items));
SELECT setval('orders_id_seq',      (SELECT MAX(id) FROM orders));
SELECT setval('order_items_id_seq', (SELECT MAX(id) FROM order_items));
