USE `fitwsarah-db`;


INSERT INTO accounts (user_id, account_id, username, email, city, date_created) VALUES
('1','uuid-acc1', 'johnsmith','johnsmith@example.com', 'New York', '2024-02-08'),
('2','uuid-acc2', 'emilyjones', 'emilyjones@example.com', 'Los Angeles', '2024-01-08'),
('3','uuid-acc3', 'michaelbrown', 'michaelbrown@example.com', 'Chicago', '2024-01-08'),
('4','uuid-acc4', 'sarahwhite', 'sarahwhite@example.com', 'Houston', '2024-02-08'),
('5','uuid-acc5', 'davidjohnson', 'davidjohnson@example.com', 'Phoenix', '2023-02-08'),
('65cd8599c2b1eb728f81e1aa', 'ac366e05-1009-4e6f-a80c-cf8a19290993', 'emile', 'emilegirard@gmail.com', 'Montreal', '2024-02-14'),
('65cd8599c2b1eb728f81e1aa', 'ac366e05-1009-4e6f-a80c-cf8a19290996', 'emile', 'emilegirardgmail.com', 'Montreal', '2024-02-14');


INSERT INTO invoices (invoice_id, account_id, user_id, username, status, date, due_date, payment_type, price) VALUES
 ('inv-uuid-1', 'uuid-acc1', '65b9b977d5fa901a57937bc3', 'Thomas', 'COMPLETED', '2024-01-01', '2024-01-10', 'Credit Card', 100.00),
('inv-uuid-2', 'uuid-acc2', '65b9b977d5fa901a57937bc3', 'Thomas', 'PENDING', '2024-01-02', '2024-01-12', 'PayPal', 150.00),
('inv-uuid-3', 'uuid-acc3', '101188348963819843921', 'michaelbrown', 'OVERDUE', '2024-01-03', '2024-01-13', 'Debit', 200.00),
('inv-uuid-4', 'uuid-acc4', '101188348963819843921', 'sarahwhite', 'COMPLETED', '2024-01-04', '2024-01-14', 'Cash', 50.00),
('inv-uuid-5', 'uuid-acc5', '101188348963819843921', 'davidjohnson', 'PENDING', '2024-01-05', '2024-01-15', 'Bank Transfer', 75.00);

INSERT INTO feedback_threads (feedback_id, user_id, stars, content, status) VALUES
('fdbk-uuid-1', 'uuid-acc1', 5, 'Excellent personal training session','INVISIBLE'),
('fdbk-uuid-2', 'uuid-acc2', 4, 'Great yoga classes, but room was crowded', 'INVISIBLE'),
('fdbk-uuid-3', 'uuid-acc3', 5, 'Loved the training package, very comprehensive', 'INVISIBLE'),
('fdbk-uuid-4', 'uuid-acc4', 3, 'Good nutrition advice, but consultation felt rushed', 'INVISIBLE'),
('fdbk-uuid-5', 'uuid-acc5', 4, 'Fun group classes, but schedule is limited','INVISIBLE');

INSERT INTO appointments (appointment_id, availability_id, user_id, service_id, status, location, first_name, last_name, phone_num, date, time) VALUES
('uuid-appt1', 'uuid-avail1', 'dc2b4f0f-76da-4d1e-ad2d-cebf950e5fa2', '99a836ab-8f83-4e63-b266-3f56b1396df4', 'REQUESTED', 'Location 1', 'Emily', 'Jones', '514-223-3322', '2023-12-01','10:00'),
('uuid-appt2', 'uuid-avail2', '7ab87f1e-7a5c-4c5a-bddc-8b1c5c8d8d9a', 'd2a5b28a-04b5-4f4e-ba27-983fb825a6f0', 'COMPLETED', 'Location 2', 'John', 'Smith', '416-555-1234', '2023-12-02','14:30'),
('uuid-appt3', 'uuid-avail3', 'f9b6c841-2d18-4d6f-902a-1c1c8a0cfa82', 'b19a7e31-90b8-4d52-b738-215d23967e34', 'CANCELLED', 'Location 3', 'Alice', 'Johnson', '647-789-4567', '2023-12-03','09:45'),
('uuid-appt4', 'uuid-avail4', '34c05e38-d218-4170-b692-10cc22d2a5e8', 'a083a37a-f40a-4f5f-b20e-2e598c0d16a4', 'SCHEDULED', 'Location 4', 'Michael', 'Miller', '905-222-5555', '2023-12-04',' 11:15'),
('uuid-appt5', 'uuid-avail5', '1a11f23e-6a9a-4a82-9d7f-0676a86953f7', '7ea12f6a-b7c4-402d-9d2b-3987b8b49a5a', 'REQUESTED', 'Location 5', 'Sophia', 'Brown', '416-789-9876', '2023-12-05','16:00'),
('uuid-appt6', 'uuid-avail6', 'fca5c48c-0ea9-4ba1-b8f7-6df74541acda', '8c4d9b17-8e25-4d2a-9b92-d88b2c18315f', 'SCHEDULED', 'Location 6', 'Daniel', 'Davis', '647-333-1111', '2023-12-06',' 13:30'),
('uuid-appt7', 'uuid-avail7', 'f485ee1d-77b7-47a7-8903-9f2055b5ee21', '5d95bf15-242b-471c-8d7e-63bb148d05c4', 'COMPLETED', 'Location 7', 'Olivia', 'Taylor', '905-876-2345', '2023-12-07','12:00'),
('uuid-appt8', 'uuid-avail8', '08f6a59a-79e1-4b1e-ba0f-6f14115ccca3', 'd701724c-3da5-4d42-9e22-944f097f77e3', 'CANCELLED', 'Location 8', 'Ethan', 'Clark', '416-444-5555', '2023-12-08',' 14:45'),
('uuid-appt9', 'uuid-avail9', 'd2a6e1ee-5b0c-403b-8e91-79d2c8228f8a', 'e4f6f0e2-3961-4c06-8f63-399bfdfc695b', 'SCHEDULED', 'Location 9', 'Mia', 'Wong', '647-987-6543', '2023-12-09','09:00'),
('uuid-appt10', 'uuid-avail10', '6598b5ed034ba209b8ce28c2', '562314d2-3a8e-4a41-9b61-7bc9731f555f', 'REQUESTED', 'Location 10', 'Liam', 'Moore', '905-333-2222', '2024-3-10','15:15'),
('uuid-appt11', 'uuid-avail11', '6598b5ed034ba209b8ce28c2', 'f09e14dd-59cf-4644-a525-93bf534c8024', 'SCHEDULED', 'Location 11', 'Ava', 'Harris', '416-876-5432', '2024-3-11 ','10:30');


INSERT INTO fitness_services (service_id, status, title_en, title_fr, duration, description_en, description_fr, other_information_en, other_information_fr, price) VALUES
('99a836ab-8f83-4e63-b266-3f56b1396df4', 'VISIBLE', 'Personal Training', 'Entraînement Personnel', '1 hour', 'One-on-one personalized training session', 'Séance d’entraînement personnalisée', 'Tailored fitness guidance by Sarah.', 'Conseils de fitness personnalisés par Sarah.', 100.00),
('99a836ab-8f83-4e63-b266-3f56b1396df5', 'VISIBLE', 'Yoga Classes', 'Cours de Yoga', '45 minutes', 'Group yoga sessions for all levels', 'Séances de yoga en groupe pour tous niveaux', 'Yoga for well-being and balance for all.', 'Yoga pour le bien-être et l’équilibre pour tous.', 50.00),
('6b91f458-9eea-4336-8832-62e0a8d38ccb', 'VISIBLE', 'Boot Camp', 'Camp d''entraînement',  '30 minutes', 'High-intensity interval training in a group setting', 'Entraînement par intervalles de haute intensité en groupe', 'High-energy workout.', 'Entraînement dynamique.', 75.00),
('b5c6caef-86b3-4a55-8984-0be52a1349c8', 'VISIBLE', 'Nutrition Planning', 'Planification Nutritionnelle', '1 hour', 'Personalized nutrition planning and guidance', 'Planification nutritionnelle personnalisée et conseils', 'Personalized health and diet plans.', 'Plans de santé et de régime personnalisés.', 60.00),
('f4f956ed-c6bf-421b-bcf0-36f7c29a848c', 'VISIBLE', 'Cycling Class', 'Cours de Vélo', '1 hour', 'Indoor cycling for fitness and endurance', 'Cyclisme en salle pour la forme et l’endurance', 'Intense cycling for strength and endurance.', 'Cyclisme intense pour la force et l’endurance.', 40.00);

INSERT INTO availabilities (availability_id,day_of_week, time) VALUES
('uuid-avail1', 'Monday','10:00'),
('uuid-avail2', 'Monday ','11:00'),
('uuid-avail3', 'Sunday ','09:30'),
('uuid-avail4', 'Thursday ','14:00'),
('uuid-avail5', 'Friday ','16:30');

INSERT INTO coach_notes (coach_note_id, account_id, user_id, username, content_en, content_fr) VALUES
('1', 'uuid-acc1', '65b9b977d5fa901a57937bc3', 'johnsmith', 'Keep up the great work with your training!', 'Continuez votre excellent travail avec votre entraînement !'),
('2', 'uuid-acc2', '65b9b977d5fa901a57937bc3', 'emilyjones', 'Remember to stay hydrated during workouts.', 'Noubliez pas de rester hydraté pendant les entraînements.'),
('3', 'uuid-acc3', '101188348963819843921', 'michaelbrown', 'Excellent progress on your last session!', 'Excellents progrès lors de votre dernière séance !'),
('4', 'uuid-acc4', '101188348963819843921', 'sarahwhite', 'Try to increase your running distance gradually.', 'Essayez d''augmenter progressivement votre distance de course.'),
('5', 'uuid-acc5', '101188348963819843921', 'davidjohnson', 'Focus on maintaining a balanced diet.', 'Concentrez-vous sur le maintien d''une alimentation équilibrée.');
