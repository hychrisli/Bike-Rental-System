package cmpe282.station.service.impl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cmpe282.station.entity.OutBike;
import cmpe282.station.entity.InBike;
import cmpe282.station.entity.RsvdBike;
import cmpe282.station.entity.StationBike;
import cmpe282.station.mapper.BikeMapper;
import cmpe282.station.mapper.MapIdMapper;
import cmpe282.station.repository.OutBikeRepository;
import cmpe282.station.repository.InBikeRepository;
import cmpe282.station.repository.RsvdBikeRepository;
import cmpe282.station.repository.StationBikeRepository;
import cmpe282.station.service.BikeService;

@Service
@Transactional
public class BikeServiceImpl implements BikeService {

    @SuppressWarnings("unused")
    private static Logger LOGGER = Logger.getLogger(BikeServiceImpl.class.getName());
    
    @Autowired
    StationBikeRepository stationBikeRepo;
    
    @Autowired
    RsvdBikeRepository rsvdBikeRepo;
    
    @Autowired
    OutBikeRepository outBikeRepo;
    
    @Autowired
    InBikeRepository inBikeRepo;
    
    @Override
    public StationBike getStationBike(String bikeId) {
	return stationBikeRepo.findOne(MapIdMapper.toMapId("bikeId", bikeId));
    }
    
    @Override
    public RsvdBike getRsvdBike(String userId) {
	return rsvdBikeRepo.findOne(MapIdMapper.toMapId("userId", userId));
    }

    @Override
    public OutBike getOutBike(String bikeId) {
	return outBikeRepo.findOne(MapIdMapper.toMapId("bikeId", bikeId));
    }
    
    private void deleteOutBike(String bikeId) {
	outBikeRepo.delete(MapIdMapper.toMapId("bikeId", bikeId));
    }

    @Override
    public InBike getInBike(String txnId) {
	return inBikeRepo.findOne(MapIdMapper.toMapId("txnId", txnId));
    }

    @Override
    public RsvdBike rsvBike(String stationId, String txnId, String userId) {
	StationBike stationBike = stationBikeRepo.findOneBike(stationId);
	if (stationBike == null ) return null;
	
	RsvdBike rsvdBike = BikeMapper.toRsvdBike(stationBike, userId, txnId);
	rsvdBikeRepo.save(rsvdBike);
	stationBikeRepo.delete(stationBike);
	return rsvdBike;
    }

    @Override
    public StationBike cancelRsvdBike(String userId) {
	RsvdBike rsvdBike = getRsvdBike(userId);
	if (rsvdBike == null) return null;
	
	StationBike stationBike = BikeMapper.toStationBike(rsvdBike);
	stationBikeRepo.save(stationBike);
	rsvdBikeRepo.delete(rsvdBike);
	return stationBike;
    }
    
    @Override
    public OutBike checkoutBike(String userId, String stationId) {
	RsvdBike rsvdBike = getRsvdBike(userId);
	if (rsvdBike == null || !rsvdBike.getStationId().equals(stationId)) 
	    return null;
	
	OutBike outBike = BikeMapper.toOutBike(rsvdBike);
	outBikeRepo.save(outBike);
	rsvdBikeRepo.delete(rsvdBike);
	return outBike;
    }

    @Override
    public void checkinBike(InBike inBike) {

	StationBike stationBike = BikeMapper.toStationBike(inBike);
	inBikeRepo.save(inBike);
	stationBikeRepo.save(stationBike);
	deleteOutBike(inBike.getBikeId());

    }

    @Override
    public InBike checkinPrepare(String bikeId, String stationId) {
	OutBike outBike = getOutBike(bikeId);
	if (outBike == null) return null;
	return BikeMapper.toInBike(outBike, stationId);
    }

}
