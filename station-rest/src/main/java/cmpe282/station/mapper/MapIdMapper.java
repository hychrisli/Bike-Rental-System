package cmpe282.station.mapper;

import java.io.Serializable;

import org.springframework.data.cassandra.repository.MapId;
import org.springframework.data.cassandra.repository.support.BasicMapId;

public class MapIdMapper {
    public static <T extends Serializable> MapId toMapId(String key, T value) {
	MapId mId = new BasicMapId();
	mId.put(key, value);
	return mId;
    }
}
