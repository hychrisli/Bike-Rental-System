DROP KEYSPACE brs;
CREATE KEYSPACE brs WITH replication={'class': 'SimpleStrategy', 'replication_factor': '1'};
USE brs;

CREATE TABLE Station ( station_id TEXT, name TEXT, total_docks INT, avail_bikes INT, PRIMARY KEY (station_id));
CREATE TABLE Bike (bike_id TEXT, station_id TEXT, is_reserved BOOLEAN, user_id TEXT, txn_id TEXT, PRIMARY KEY ((station_id, bike_id)));
CREATE TABLE RentHist (txn_id TEXT, bike_id TEXT, user_id TEXT, from_station_id TEXT, to_station_id INT, checkout_time TIMESTAMP, checkin_time TIMESTAMP, PRIMARY KEY (txn_id));


INSERT INTO Station (station_id, name, total_docks, avail_bikes) VALUES ('s001', 'King Library', 8, 6);
INSERT INTO Station (station_id, name, total_docks, avail_bikes) VALUES ('s002', 'San Fernando & 7th', 8, 4);
INSERT INTO Station (station_id, name, total_docks, avail_bikes) VALUES ('s003', 'San Salvador & 9th', 8, 0);

INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES ('b0001', 's001', false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES ('b0002', 's001', false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES ('b0003', 's001', false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES ('b0004', 's001', false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES ('b0005', 's001', false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES ('b0006', 's001', false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES ('b0007', 's002', false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES ('b0008', 's002', false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES ('b0009', 's002', false, null, null);
INSERT INTO Bike (bike_id, station_id, is_reserved, user_id, txn_id) VALUES ('b0010', 's002', false, null, null);

 
