package cmpe282.station.respository;

import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cmpe282.station.entity.StationDetail;

@Repository
public interface StationDetailRepository extends CrudRepository<StationDetail, MapId> {

}
