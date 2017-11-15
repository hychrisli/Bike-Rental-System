package cmpe282.station.service.impl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cmpe282.station.entity.Station;
import cmpe282.station.mapper.MapIdMapper;
import cmpe282.station.repository.StationRepository;
import cmpe282.station.service.StationService;

@Service
public class StationServiceImpl implements StationService {

    private static Logger LOGGER = Logger.getLogger(StationServiceImpl.class.getName());
    
    @Autowired
    private StationRepository repo;
    
    @Override
    public Station findStationDetail(int station_id) {
	LOGGER.info("My station Id" + station_id);
	return repo.findOne(MapIdMapper.toMapId("station_id", station_id));
    }
}
