package cmpe282.station.service;

import org.springframework.data.cassandra.repository.MapId;

public interface MapIdService {
	
	public MapId toMapId(Long id);
}
