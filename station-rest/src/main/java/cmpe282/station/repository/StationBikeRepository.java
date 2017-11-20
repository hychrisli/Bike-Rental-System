package cmpe282.station.repository;

import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cmpe282.station.entity.StationBike;

@Repository
public interface StationBikeRepository extends CrudRepository<StationBike, MapId> {

    @Query("SELECT * FROM StationBike WHERE station_id = ?0 LIMIT 1 ALLOW FILTERING")
    public StationBike findOneBike(String stationId);
}
