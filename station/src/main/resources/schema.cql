CREATE KEYSPACE station WITH replication={'class': 'SimpleStrategy', 'replication_factor': '1'};
USE station;

CREATE TABLE StationDetail ( station_id int, name text, latitude float, longitude float, total_docks int, avail_biks int, PRIMARY KEY (station_id));
 