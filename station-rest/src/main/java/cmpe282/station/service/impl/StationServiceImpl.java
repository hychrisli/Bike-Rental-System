package cmpe282.station.service.impl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmpe282.message.direct.CheckinConfirmMsg;
import cmpe282.message.direct.CheckinReqMsg;
import cmpe282.message.direct.CheckoutConfirmMsg;
import cmpe282.message.direct.CheckoutReqMsg;
import cmpe282.message.mq.ConfirmMsg;
import cmpe282.message.mq.ReservMsg;
import cmpe282.station.entity.InBike;
import cmpe282.station.entity.OutBike;
import cmpe282.station.entity.RsvdBike;
import cmpe282.station.entity.Station;
import cmpe282.station.mapper.CheckoutMsgMapper;
import cmpe282.station.mapper.ComplMsgMapper;
import cmpe282.station.mapper.ConfirmMsgMapper;
import cmpe282.station.mapper.MapIdMapper;
import cmpe282.station.repository.StationRepository;
import cmpe282.station.service.BikeService;
import cmpe282.station.service.StationService;

@Service
@Transactional
public class StationServiceImpl implements StationService {

    private static Logger LOGGER = Logger.getLogger(StationServiceImpl.class.getName());
    
    @Autowired
    private StationRepository stationRepo;
    
    @Autowired
    private BikeService bikeSvc;
    
    @Override
    public Station getStation(String stationId) {
	return stationRepo.findOne(MapIdMapper.toMapId("stationId", stationId));
    }

    @Override
    public boolean updateAvailBikes(String stationId, int delta) {
	Station station = getStation(stationId);
	int availBikes = station.getAvailBikes() + delta;
	
	if ( availBikes < 0 || availBikes > station.getTotalDocks())
	    return false;
	
	station.setAvailBikes(availBikes);
	stationRepo.save(station);
	return true;
    }

    @Override
    public boolean increaseAvailBikesByOne(String stationId) {
	return updateAvailBikes(stationId, 1);
    }

    @Override
    public boolean decreaseAvailBikesByOne(String stationId) {
	return updateAvailBikes(stationId, -1);
    }

    @Override
    public ConfirmMsg reserveOneBike(ReservMsg reservMsg) {
	
	Station station = getStation(reservMsg.getStationId());
	
	if (station == null || station.getAvailBikes() < 1)
	    return ConfirmMsgMapper.toNotOkMsg(reservMsg);
	
	RsvdBike rsvdBike = bikeSvc.rsvBike(
		reservMsg.getStationId(), 
		reservMsg.getTransactionId(), 
		reservMsg.getUserId());
	    
	if (rsvdBike != null && decreaseAvailBikesByOne(rsvdBike.getStationId()))
	    return ConfirmMsgMapper.toOkMsg(rsvdBike);
	
	return ConfirmMsgMapper.toNotOkMsg(reservMsg);
    }

    @Override
    public CheckoutConfirmMsg checkoutOneBike(CheckoutReqMsg checkoutReqMsg) {
	OutBike outBike = bikeSvc.checkoutBike(checkoutReqMsg.getUserId());
	if (outBike == null) return CheckoutMsgMapper.toNotOkMsg();
	else return CheckoutMsgMapper.toOkMsg(outBike);
    }

    @Override
    public CheckinConfirmMsg checkinOneBike(CheckinReqMsg checkinMsg) {
	Station station = getStation(checkinMsg.getStationId());
	if (station == null || station.getAvailBikes() >= station.getTotalDocks()) 
	    return ComplMsgMapper.toNotOkCheckinMsg();
	
	InBike inBike = bikeSvc.checkinBike(checkinMsg.getBikeId(), checkinMsg.getStationId());
	if (inBike == null) return ComplMsgMapper.toNotOkCheckinMsg();
	
	increaseAvailBikesByOne(inBike.getToStationId());
	return ComplMsgMapper.toOkCheckinMsg(inBike);
    }
}
