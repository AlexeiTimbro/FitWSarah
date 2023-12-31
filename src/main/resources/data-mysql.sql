USE `fitwsarah-db`;


INSERT INTO accounts (user_id, account_id, username, email, city) VALUES
('1','uuid-acc1', 'johnsmith','johnsmith@example.com', 'New York'),
('2','uuid-acc2', 'emilyjones', 'emilyjones@example.com', 'Los Angeles'),
('3','uuid-acc3', 'michaelbrown', 'michaelbrown@example.com', 'Chicago'),
('4','uuid-acc4', 'sarahwhite', 'sarahwhite@example.com', 'Houston'),
('5','uuid-acc5', 'davidjohnson', 'davidjohnson@example.com', 'Phoenix');


INSERT INTO admin_accounts (admin_id, username, password, email, role, city) VALUES
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

INSERT INTO feeback_threads (feedback_id, account_id, stars, content) VALUES
('fdbk-uuid-1', 'uuid-acc1', 5, 'Excellent personal training session'),
('fdbk-uuid-2', 'uuid-acc2', 4, 'Great yoga classes, but room was crowded'),
('fdbk-uuid-3', 'uuid-acc3', 5, 'Loved the training package, very comprehensive'),
('fdbk-uuid-4', 'uuid-acc4', 3, 'Good nutrition advice, but consultation felt rushed'),
('fdbk-uuid-5', 'uuid-acc5', 4, 'Fun group classes, but schedule is limited');

INSERT INTO appointments (appointment_id, availability_id, account_id, admin_id, service_id, status, location) VALUES
('uuid-appt1', 'uuid-avail1', 'dc2b4f0f-76da-4d1e-ad2d-cebf950e5fa2', 'uuid-admin1', '99a836ab-8f83-4e63-b266-3f56b1396df4', 'SCHEDULED', 'Location 1'),
('uuid-appt2', 'uuid-avail2', 'dc2b4f0f-76da-4d1e-ad2d-cebf950e5fa2', 'uuid-admin2', '99a836ab-8f83-4e63-b266-3f56b1396df5', 'SCHEDULED', 'Location 2'),
('uuid-appt3', 'uuid-avail3', 'dc2b4f0f-76da-4d1e-ad2d-cebf950e5fa2', 'uuid-admin3', '6b91f458-9eea-4336-8832-62e0a8d38ccb', 'COMPLETED', 'Location 3'),
('uuid-appt4', 'uuid-avail4', 'dc2b4f0f-76da-4d1e-ad2d-cebf950e5fa2', 'uuid-admin4', 'b5c6caef-86b3-4a55-8984-0be52a1349c8', 'SCHEDULED', 'Location 4'),
('uuid-appt5', 'uuid-avail5', 'dc2b4f0f-76da-4d1e-ad2d-cebf950e5fa2', 'uuid-admin5', 'f4f956ed-c6bf-421b-bcf0-36f7c29a848c', 'CANCELLED', 'Location 5');


INSERT INTO fitness_services (service_id, promo_id, title, duration, description, other_information, price) VALUES
('99a836ab-8f83-4e63-b266-3f56b1396df4', 'a062ff08-d00f-47d9-88d3-e35e7d0446a3', 'Personal Training', '1 hour', 'One-on-one personalized training session','This is a one-on-one training session with Sarah where she will guide you through a personal specialized training that will meet your goals.', 100.00),
('99a836ab-8f83-4e63-b266-3f56b1396df5', '9ecab68a-fe7d-40c2-bd4f-62d2854ad92d', 'Yoga Classes', '45 minutes', 'Group yoga sessions for all levels','Join our group yoga classes, perfect for all skill levels, guided by our expert Sarah to enhance your flexibility, balance, and peace of mind.', 50.00),
('6b91f458-9eea-4336-8832-62e0a8d38ccb', '2644a7cb-59ce-4f8d-9db2-36e0a06b6508', 'Boot Camp', '30 minutes', 'High-intensity interval training in a group setting','Experience our boot camp, a high-energy, group-oriented fitness boot camp program led by Sarah, designed to push you to your limits for maximum results.', 75.00),
('b5c6caef-86b3-4a55-8984-0be52a1349c8', '29ebf549-2caf-4919-ba15-9c5904534dc1', 'Nutrition Planning', '1 hour', 'Personalized nutrition planning and guidance','Get personalized nutrition plans and guidance from our expert nutritionist Sarah, tailored to your individual health goals and dietary needs.', 60.00),
('f4f956ed-c6bf-421b-bcf0-36f7c29a848c', '1de4e220-af32-4f0d-94eb-1b906bb8b2e1', 'Cycling Class', '1 hour', 'Indoor cycling for fitness and endurance','Our indoor cycling class, led by cycling pro Sarah, focuses on endurance, strength, intervals, and high intensity workouts for fitness enthusiasts.', 40.00);

INSERT INTO promo_offers (promotion_id, title, availability, description, price) VALUES
('a062ff08-d00f-47d9-88d3-e35e7d0446a3', 'New Year Fitness Special', 1, 'Discount on personal training sessions for new members', 80.00),
('9ecab68a-fe7d-40c2-bd4f-62d2854ad92d', 'Summer Yoga Deal', 1, 'Unlimited yoga classes for the summer season', 120.00),
('2644a7cb-59ce-4f8d-9db2-36e0a06b6508', 'Boot Camp Blast', 0, 'Intensive boot camp course for advanced practitioners', 150.00),
('29ebf549-2caf-4919-ba15-9c5904534dc1', 'Nutrition Newcomer', 1, 'First-time consultation discount for nutrition planning', 45.00),
('1de4e220-af32-4f0d-94eb-1b906bb8b2e1', 'Cycling Challenge', 1, 'Join our monthly cycling challenge and win prizes', 30.00);

INSERT INTO availabilities (availability_id, available, admin_id, datetime) VALUES
('uuid-avail1', 1, 'uuid-admin1', '2023-12-01 10:00:00'),
('uuid-avail2', 0, 'uuid-admin2', '2023-12-02 11:00:00'),
('uuid-avail3', 1, 'uuid-admin3', '2023-12-03 09:30:00'),
('uuid-avail4', 0, 'uuid-admin4', '2023-12-04 14:00:00'),
('uuid-avail5', 1, 'uuid-admin5', '2023-12-05 16:30:00');

