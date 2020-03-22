/* CLEAR TABLES */	
DELETE FROM provided_facilities;	
DELETE FROM provided_room;	
DELETE FROM reservation;
DELETE FROM invoice;
DELETE FROM person;
DELETE FROM room;
DELETE FROM facilities;
DELETE FROM hotel;

/* HOTEL DATA */
INSERT INTO hotel (id, hotel_name, "version") VALUES(1, 'Grand', 0);
INSERT INTO hotel (id, hotel_name, "version") VALUES(2, 'Eleon', 0);

/* PERSON DATA */
INSERT INTO person (id, person_name, "version", "admin", blacklisted, encryted_password) VALUES(1, 'admin', 0, true, false, '');
INSERT INTO person (id, person_name, "version", "admin", blacklisted, encryted_password) VALUES(2, 'user', 0, false, false, '');

/* FACILITIES DATA */
INSERT INTO facilities (id, service_name, service_number, service_price, "version", hotel_id) VALUES(1, 'cleaning', '001', 10, 0, 1);
INSERT INTO facilities (id, service_name, service_number, service_price, "version", hotel_id) VALUES(2, 'washing', '002', 50, 0, 1);
INSERT INTO facilities (id, service_name, service_number, service_price, "version", hotel_id) VALUES(3, 'allinc', '003', 200, 0, 1);

INSERT INTO facilities (id, service_name, service_number, service_price, "version", hotel_id) VALUES(4, 'cleaning', '001', 10, 0, 2);
INSERT INTO facilities (id, service_name, service_number, service_price, "version", hotel_id) VALUES(5, 'washing', '002', 50, 0, 2);
INSERT INTO facilities (id, service_name, service_number, service_price, "version", hotel_id) VALUES(6, 'allinc', '003', 200, 0, 2);

/* ROOMS DATA */
INSERT INTO room (id, room_number, "version", hotel_id) VALUES(1, '001', 0, 1);
INSERT INTO room (id, room_number, "version", hotel_id) VALUES(2, '002', 0, 1);
INSERT INTO room (id, room_number, "version", hotel_id) VALUES(3, '003', 0, 1);
INSERT INTO room (id, room_number, "version", hotel_id) VALUES(4, '004', 0, 1);
INSERT INTO room (id, room_number, "version", hotel_id) VALUES(5, '005', 0, 1);

INSERT INTO room (id, room_number, "version", hotel_id) VALUES(6, '1', 0, 2);
INSERT INTO room (id, room_number, "version", hotel_id) VALUES(7, '2', 0, 2);
INSERT INTO room (id, room_number, "version", hotel_id) VALUES(8, '3', 0, 2);
INSERT INTO room (id, room_number, "version", hotel_id) VALUES(9, '4', 0, 2);
INSERT INTO room (id, room_number, "version", hotel_id) VALUES(10, '5', 0, 2);

/* INVOICE DATA */
INSERT INTO invoice (id, date_begin, date_end, invoice_number, "version", person_id) VALUES(1, '2019-01-01', '2019-01-03', '100', 0, 2);
INSERT INTO invoice (id, date_begin, date_end, invoice_number, "version", person_id) VALUES(2, '2019-02-01', '2019-02-03', '101', 0, 2);

/* PROVIDED FACILITIES */
INSERT INTO provided_facilities (id, cost_facilities, count_facilities, date_facilities, "version", facilities_id, invoice_id) VALUES(1, 10, 1, '2019-01-01', 0, 1, 1);
INSERT INTO provided_facilities (id, cost_facilities, count_facilities, date_facilities, "version", facilities_id, invoice_id) VALUES(2, 10, 1, '2019-01-02', 0, 1, 1);
INSERT INTO provided_facilities (id, cost_facilities, count_facilities, date_facilities, "version", facilities_id, invoice_id) VALUES(3, 50, 1, '2019-01-01', 0, 2, 1);
INSERT INTO provided_facilities (id, cost_facilities, count_facilities, date_facilities, "version", facilities_id, invoice_id) VALUES(4, 10, 1, '2019-01-03', 0, 1, 1);

INSERT INTO provided_facilities (id, cost_facilities, count_facilities, date_facilities, "version", facilities_id, invoice_id) VALUES(5, 10, 1, '2019-02-01', 0, 1, 2);
INSERT INTO provided_facilities (id, cost_facilities, count_facilities, date_facilities, "version", facilities_id, invoice_id) VALUES(6, 200, 1, '2019-02-02', 0, 3, 2);
INSERT INTO provided_facilities (id, cost_facilities, count_facilities, date_facilities, "version", facilities_id, invoice_id) VALUES(7, 50, 1, '2019-02-01', 0, 2, 2);
INSERT INTO provided_facilities (id, cost_facilities, count_facilities, date_facilities, "version", facilities_id, invoice_id) VALUES(8, 10, 1, '2019-02-03', 0, 1, 2);

/* PROVIDED ROOMS */
INSERT INTO provided_room (id, cost_room, date_room, "version", invoice_id, room_id) VALUES(1, 500, '2019-01-01', 0, 1, 3);
INSERT INTO provided_room (id, cost_room, date_room, "version", invoice_id, room_id) VALUES(2, 500, '2019-01-02', 0, 1, 3);
INSERT INTO provided_room (id, cost_room, date_room, "version", invoice_id, room_id) VALUES(3, 500, '2019-01-03', 0, 1, 3);

INSERT INTO provided_room (id, cost_room, date_room, "version", invoice_id, room_id) VALUES(4, 400, '2019-02-01', 0, 2, 4);
INSERT INTO provided_room (id, cost_room, date_room, "version", invoice_id, room_id) VALUES(5, 400, '2019-02-02', 0, 2, 4);
INSERT INTO provided_room (id, cost_room, date_room, "version", invoice_id, room_id) VALUES(6, 400, '2019-02-03', 0, 2, 4);

/* RESERVATION DATA */
INSERT INTO reservation (id, date_begin, date_end, "version", person_id, room_id) VALUES(1, '2020-06-06', '2020-06-12', 0, 2, 7);
INSERT INTO reservation (id, date_begin, date_end, "version", person_id, room_id) VALUES(2, '2020-06-06', '2020-06-12', 0, 2, 9);
