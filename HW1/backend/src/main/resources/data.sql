INSERT INTO user (name, phone) VALUES ('Pedro', '123123123');
INSERT INTO user (name, phone) VALUES ('Novato', '123456789');

INSERT INTO city (name) VALUES ('Aveiro');
INSERT INTO city (name) VALUES ('Porto');
INSERT INTO city (name) VALUES ('Coimbra');
INSERT INTO city (name) VALUES ('Faro');

INSERT INTO bus (name, capacity) VALUES ('XPTO', 10);
INSERT INTO bus (name, capacity) VALUES ('Autocarro Alberto', 3);
INSERT INTO bus (name, capacity) VALUES ('FlixBus', 5);

INSERT INTO trip (date, price, occupancy, origin_id, destination_id, bus_id) 
VALUES ('2024-04-01', 5.20, 0, 1, 3, 1);

INSERT INTO trip (date, price, occupancy, origin_id, destination_id, bus_id) 
VALUES ('2024-04-01', 7.20, 0, 1, 3, 2);

INSERT INTO trip (date, price, occupancy, origin_id, destination_id, bus_id) 
VALUES ('2024-04-01', 9.25, 0, 1, 3, 3);

INSERT INTO trip (date, price, occupancy, origin_id, destination_id, bus_id) 
VALUES ('2024-04-01', 15.00, 0, 2, 3, 2);

INSERT INTO trip (date, price, occupancy, origin_id, destination_id, bus_id) 
VALUES ('2024-04-01', 12.25, 0, 2, 4, 3);


