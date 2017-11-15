DROP KEYSPACE brs;
CREATE KEYSPACE brs WITH replication={'class': 'SimpleStrategy', 'replication_factor': '1'};
USE brs;

CREATE TABLE Station ( station_id int, name text, latitude float, longitude float, total_docks int, avail_bikes int, PRIMARY KEY (station_id));
INSERT INTO Station (station_id, name, latitude, longitude, total_docks, avail_bikes) VALUES (1, 'King Library', 37.335939, -121.885699, 8, 6);
 
