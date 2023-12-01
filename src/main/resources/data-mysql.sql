USE `fitwsarah-db`;

INSERT INTO accounts (account_id, username, password, email, city) VALUES
('uuid-acc1', 'johnsmith', 'pass123', 'johnsmith@example.com', 'New York'),
('uuid-acc2', 'emilyjones', 'pass234', 'emilyjones@example.com', 'Los Angeles'),
('uuid-acc3', 'michaelbrown', 'pass345', 'michaelbrown@example.com', 'Chicago'),
('uuid-acc4', 'sarahwhite', 'pass456', 'sarahwhite@example.com', 'Houston'),
('uuid-acc5', 'davidjohnson', 'pass567', 'davidjohnson@example.com', 'Phoenix');

INSERT INTO adminAccounts (admin_id, username, password, email, role, city) VALUES
('uuid-admin1', 'admin1', 'adminpass1', 'admin1@example.com', 'Administrator', 'New York'),
('uuid-admin2', 'admin2', 'adminpass2', 'admin2@example.com', 'Manager', 'Los Angeles'),
('uuid-admin3', 'admin3', 'adminpass3', 'admin3@example.com', 'Coordinator', 'Chicago'),
('uuid-admin4', 'admin4', 'adminpass4', 'admin4@example.com', 'Supervisor', 'Houston'),
('uuid-admin5', 'admin5', 'adminpass5', 'admin5@example.com', 'Director', 'Phoenix');

INSERT INTO invoices (invoice_id, account_id, amount, content) VALUES
('inv-uuid-1', 'uuid-acc1', 100.00, 'Fitness Training Session'),
('inv-uuid-2', 'uuid-acc2', 150.00, 'Yoga Class Subscription'),
('inv-uuid-3', 'uuid-acc3', 200.00, 'Personal Training Package'),
('inv-uuid-4', 'uuid-acc4', 50.00, 'Nutrition Consultation'),
('inv-uuid-5', 'uuid-acc5', 75.00, 'Group Fitness Classes');

INSERT INTO feebackThreads (feedback_id, account_id, stars, content) VALUES
('fdbk-uuid-1', 'uuid-acc1', 5, 'Excellent personal training session'),
('fdbk-uuid-2', 'uuid-acc2', 4, 'Great yoga classes, but room was crowded'),
('fdbk-uuid-3', 'uuid-acc3', 5, 'Loved the training package, very comprehensive'),
('fdbk-uuid-4', 'uuid-acc4', 3, 'Good nutrition advice, but consultation felt rushed'),
('fdbk-uuid-5', 'uuid-acc5', 4, 'Fun group classes, but schedule is limited');

INSERT INTO appointments (appointment_id, availability_id, admin_id, service_id, status, location) VALUES
('uuid-appt1', 'uuid-avail1', 'uuid-admin1', 'uuid-service1', 'Scheduled', 'Location 1'),
('uuid-appt2', 'uuid-avail2', 'uuid-admin2', 'uuid-service2', 'Completed', 'Location 2'),
('uuid-appt3', 'uuid-avail3', 'uuid-admin3', 'uuid-service3', 'Cancelled', 'Location 3'),
('uuid-appt4', 'uuid-avail4', 'uuid-admin4', 'uuid-service4', 'Scheduled', 'Location 4'),
('uuid-appt5', 'uuid-avail5', 'uuid-admin5', 'uuid-service5', 'Completed', 'Location 5');


INSERT INTO fitnessServices (service_id, promotion_id, title, duration, description, price) VALUES
('service-uuid-1', 'promo-uuid-1', 'Personal Training', '1 hour', 'One-on-one personalized training session', 100.00),
('service-uuid-2', 'promo-uuid-2', 'Yoga Classes', '45 minutes', 'Group yoga sessions for all levels', 50.00),
('service-uuid-3', 'promo-uuid-3', 'Boot Camp', '30 minutes', 'High-intensity interval training in a group setting', 75.00),
('service-uuid-4', 'promo-uuid-4', 'Nutrition Planning', '1 hour', 'Personalized nutrition planning and guidance', 60.00),
('service-uuid-5', 'promo-uuid-5', 'Cycling Class', '1 hour', 'Indoor cycling for fitness and endurance', 40.00);

INSERT INTO promoOffers (promotion_id, title, availability, description, price) VALUES
('promo-uuid-1', 'New Year Fitness Special', 1, 'Discount on personal training sessions for new members', 80.00),
('promo-uuid-2', 'Summer Yoga Deal', 1, 'Unlimited yoga classes for the summer season', 120.00),
('promo-uuid-3', 'Boot Camp Blast', 0, 'Intensive boot camp course for advanced practitioners', 150.00),
('promo-uuid-4', 'Nutrition Newcomer', 1, 'First-time consultation discount for nutrition planning', 45.00),
('promo-uuid-5', 'Cycling Challenge', 1, 'Join our monthly cycling challenge and win prizes', 30.00);

INSERT INTO availabilities (availability_id, available, admin_id, datetime) VALUES
('uuid-avail1', 1, 'uuid-admin1', '2023-12-01 10:00:00'),
('uuid-avail2', 0, 'uuid-admin2', '2023-12-02 11:00:00'),
('uuid-avail3', 1, 'uuid-admin3', '2023-12-03 09:30:00'),
('uuid-avail4', 0, 'uuid-admin4', '2023-12-04 14:00:00'),
('uuid-avail5', 1, 'uuid-admin5', '2023-12-05 16:30:00');

