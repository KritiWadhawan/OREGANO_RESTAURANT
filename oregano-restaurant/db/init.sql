-- Create tables for OREGANO app and preload data
CREATE TABLE IF NOT EXISTS menu_category (
  id INT PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS menu_item (
  id INT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(255),
  price DOUBLE NOT NULL,
  category_id INT,
  FOREIGN KEY (category_id) REFERENCES menu_category(id)
);

CREATE TABLE IF NOT EXISTS orders (
  id INT PRIMARY KEY AUTO_INCREMENT,
  address VARCHAR(255) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_items (
  id INT PRIMARY KEY AUTO_INCREMENT,
  order_id INT,
  menu_item_id INT,
  quantity INT,
  FOREIGN KEY (order_id) REFERENCES orders(id),
  FOREIGN KEY (menu_item_id) REFERENCES menu_item(id)
);

-- Preload categories
INSERT INTO menu_category (id, name) VALUES
(1, 'Starters'),
(2, 'Main Courses'),
(3, 'Drinks')
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- Starters (ids 101-110)
INSERT INTO menu_item (id, name, description, price, category_id) VALUES
(101, 'Garlic Bread', 'Toasted garlic butter bread', 12, 1),
(102, 'Hummus', 'Creamy chickpea dip with olive oil', 15, 1),
(103, 'Stuffed Grape Leaves', 'Rice and herbs wrapped in vine leaves', 18, 1),
(104, 'Chicken Wings', 'Spicy marinated wings', 28, 1),
(105, 'Falafel Balls', 'Deep-fried chickpea patties', 25, 1),
(106, 'Cheese Platter', 'Selection of cheeses', 40, 1),
(107, 'Soup of the Day', 'Chef special soup', 20, 1),
(108, 'Spring Rolls', 'Vegetable spring rolls', 22, 1),
(109, 'Caesar Salad', 'Crisp romaine and parmesan', 30, 1),
(110, 'Mixed Olives', 'Marinated assorted olives', 10, 1)
ON DUPLICATE KEY UPDATE name=VALUES(name), price=VALUES(price);

-- Main Courses (ids 201-210)
INSERT INTO menu_item (id, name, description, price, category_id) VALUES
(201, 'Lamb Kebab', 'Grilled seasoned lamb skewers', 60, 2),
(202, 'Grilled Chicken', 'Herb-marinated grilled chicken', 55, 2),
(203, 'Beef Stroganoff', 'Creamy mushroom beef', 70, 2),
(204, 'Seafood Paella', 'Mixed seafood with saffron rice', 85, 2),
(205, 'Vegetable Stir Fry', 'Seasonal vegetables in soy sauce', 45, 2),
(206, 'Fish and Chips', 'Battered fish with fries', 50, 2),
(207, 'Spaghetti Carbonara', 'Classic creamy carbonara', 48, 2),
(208, 'Chicken Biryani', 'Fragrant biryani with chicken', 58, 2),
(209, 'Margherita Pizza', 'Tomato, basil, mozzarella', 40, 2),
(210, 'Mushroom Risotto', 'Creamy arborio rice with mushrooms', 65, 2)
ON DUPLICATE KEY UPDATE name=VALUES(name), price=VALUES(price);

-- Drinks (ids 301-310)
INSERT INTO menu_item (id, name, description, price, category_id) VALUES
(301, 'Mint Lemonade', 'Fresh mint and lemon', 15, 3),
(302, 'Mango Smoothie', 'Creamy mango blend', 18, 3),
(303, 'Espresso Coffee', 'Strong espresso shot', 12, 3),
(304, 'Green Tea', 'Soothing green tea', 10, 3),
(305, 'Mineral Water', 'Bottle of mineral water', 8, 3),
(306, 'Orange Juice', 'Fresh squeezed orange', 14, 3),
(307, 'Red Wine (glass)', 'House red wine', 35, 3),
(308, 'White Wine (glass)', 'House white wine', 30, 3),
(309, 'Soft Drink', 'Carbonated soft drink', 10, 3),
(310, 'Sparkling Water', 'Bottle of sparkling water', 9, 3)
ON DUPLICATE KEY UPDATE name=VALUES(name), price=VALUES(price);
