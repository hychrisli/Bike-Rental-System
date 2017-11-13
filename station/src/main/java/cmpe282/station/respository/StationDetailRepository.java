package cmpe282.station.respository;

import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.repository.CrudRepository;

import cmpe282.station.entity.StationDetail;

public interface StationDetailRepository extends CrudRepository<StationDetail, MapId> {

}
