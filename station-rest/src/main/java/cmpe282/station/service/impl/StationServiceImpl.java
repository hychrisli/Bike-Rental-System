package cmpe282.station.service.impl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cmpe282.message.ComplMsg;
import cmpe282.message.ConfirmMsg;
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
    public Station getStation(int station_id) {
	LOGGER.info("My station Id" + station_id);
	return repo.findOne(MapIdMapper.toMapId("station_id", station_id));
    }

    @Override
    public boolean updateAvailBikes(int stationId, int delta) {
	Station station = getStation(stationId);
	int availBikes = station.getAvail_bikes() + delta;
	
	if ( availBikes < 0 || availBikes > station.getTotal_docks())
	    return false;
	
	station.setAvail_bikes(availBikes);
	repo.save(station);
	return true;
    }

    @Override
    public boolean increaseAvailBikesByOne(int stationId) {
	return updateAvailBikes(stationId, 1);
    }

    @Override
    public boolean decreaseAvailBikesByOne(int stationId) {
	return updateAvailBikes(stationId, -1);
    }

    @Override
    public ConfirmMsg reserveOneBike(int stationId, String txnId, String userId) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean checkoutOneBike(int stationId, int bikeId, String userId) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public ComplMsg checkinOneBike(int stationId, int bikeId, String userId) {
	// TODO Auto-generated method stub
	return null;
    }
}
