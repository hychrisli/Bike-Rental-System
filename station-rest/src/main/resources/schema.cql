DROP KEYSPACE brs;
CREATE KEYSPACE brs WITH replication={'class': 'SimpleStrategy', 'replication_factor': '1'};
USE brs;

CREATE TABLE Station ( station_id INT, name TEXT, total_docks INT, avail_bikes INT, PRIMARY KEY (station_id));
CREATE TABLE Bike (bike_id INT, station_id INT, is_reserved BOOLEAN, user_id TEXT, txn_id TEXT, PRIMARY KEY (bike_id));
CREATE TABLE RentHist (txn_id TEXT, bike_id INT, user_id TEXT, from_station_id INT, to_station_id INT, checkout_time TIMESTAMP, checkin_time TIMESTAMP, PRIMARY KEY (txn_id));


INSERT INTO Station (station_id, name, total_docks, avail_bikes) VALUES (1, 'King Library', 8, 6);
INSERT INTO Station (station_id, name, total_docks, avail_bikes) VALUES (2, 'San Fernando & 7th', 8, 4);
INSERT INTO Station (station_id, name, total_docks, avail_bikes) VALUES (3, 'San Salvador & 9th', 8, 0);

INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES (1, 1, false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES (2, 1, false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES (3, 1, false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES (4, 1, false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES (5, 1, false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES (6, 1, false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES (7, 2, false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES (8, 2, false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES (9, 2, false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES (10, 2, false, null, null);

 
