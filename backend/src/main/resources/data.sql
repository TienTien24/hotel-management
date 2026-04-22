-- Rooms
INSERT INTO room (room_number, type, price, status) VALUES ('101', 'Single', 50.0, 'AVAILABLE');
INSERT INTO room (room_number, type, price, status) VALUES ('102', 'Double', 80.0, 'AVAILABLE');
INSERT INTO room (room_number, type, price, status) VALUES ('201', 'Suite', 150.0, 'AVAILABLE');
INSERT INTO room (room_number, type, price, status) VALUES ('202', 'Deluxe', 120.0, 'AVAILABLE');
INSERT INTO room (room_number, type, price, status) VALUES ('301', 'Suite', 250.0, 'MAINTENANCE');

-- Services
INSERT INTO hotel_service (name, price, description) VALUES ('Breakfast', 10.0, 'Morning buffet');
INSERT INTO hotel_service (name, price, description) VALUES ('Laundry', 5.0, 'Wash and dry');
INSERT INTO hotel_service (name, price, description) VALUES ('Spa', 30.0, 'Full body massage');
INSERT INTO hotel_service (name, price, description) VALUES ('Airport Transfer', 20.0, 'Pick up or drop off');
