package cmpe282.station.repository;

import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cmpe282.station.entity.Station;

@Repository
public interface StationRepository extends CrudRepository<Station, MapId> {

}
