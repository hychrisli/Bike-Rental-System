package cmpe282.station.repository;

import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cmpe282.station.entity.Bike;

@Repository
public interface BikeRepository extends CrudRepository<Bike, MapId> {

    @Query("SELECT * FROM Bike WHERE station_id = ?0 and is_reserved = false LIMIT 1 ALLOW FILTERING")
    public Bike findOneBike(int stationId);
}
