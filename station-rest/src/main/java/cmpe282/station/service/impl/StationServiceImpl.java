package cmpe282.station.service.impl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmpe282.message.ComplMsg;
import cmpe282.message.ConfirmMsg;
import cmpe282.message.ReservMsg;
import cmpe282.station.entity.Bike;
import cmpe282.station.entity.Station;
import cmpe282.station.mapper.ConfirmMsgMapper;
import cmpe282.station.mapper.MapIdMapper;
import cmpe282.station.repository.StationRepository;
import cmpe282.station.service.BikeService;
import cmpe282.station.service.StationService;

@Service
@Transactional
public class StationServiceImpl implements StationService {

    // private static Logger LOGGER = Logger.getLogger(StationServiceImpl.class.getName());
    
    @Autowired
    private StationRepository stationRepo;
    
    @Autowired
    private BikeService bikeSvc;
    
    @Override
    public Station getStation(int stationId) {
	return stationRepo.findOne(MapIdMapper.toMapId("stationId", stationId));
    }

    @Override
    public boolean updateAvailBikes(int stationId, int delta) {
	Station station = getStation(stationId);
	int availBikes = station.getAvailBikes() + delta;
	
	if ( availBikes < 0 || availBikes > station.getTotalDocks())
	    return false;
	
	station.setAvailBikes(availBikes);
	stationRepo.save(station);
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
    public ConfirmMsg reserveOneBike(ReservMsg reservMsg) {
	
	Station station = getStation(reservMsg.getStationId());
	
	if (station == null || station.getAvailBikes() < 1)
	    return ConfirmMsgMapper.toNotOkMsg(reservMsg);
	
	Bike bike = bikeSvc.reserveBike(
		reservMsg.getStationId(), 
		reservMsg.getTransactionId(), 
		reservMsg.getUserId());
	    
	if (bike != null && decreaseAvailBikesByOne(bike.getStationId()))
	    return ConfirmMsgMapper.toOkMsg(bike);
	
	return ConfirmMsgMapper.toNotOkMsg(reservMsg);
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
