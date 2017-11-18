package cmpe282.station.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.MapId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmpe282.station.entity.Bike;
import cmpe282.station.mapper.MapIdMapper;
import cmpe282.station.repository.BikeRepository;
import cmpe282.station.service.BikeService;

@Service
@Transactional
public class BikeServiceImpl implements BikeService {

    @Autowired
    BikeRepository bikeRepo;
    
    @Override
    public Bike getBike(int bikeId) {
	return bikeRepo.findOne(MapIdMapper.toMapId("bikeId", bikeId));
    }

    @Override
    public Bike reserveBike(int stationId, String txnId, String userId) {
	Bike bike = bikeRepo.findOneBike(stationId);

	if (bike == null)
	    return null;
	
	bike.setReserved(true);
	bike.setTxnId(txnId);
	bike.setUserId(userId);
	bikeRepo.save(bike);
	
	return bike;
    }

    @Override
    public boolean checkoutBike(int userId, int bikeId, int stationId) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean checkinBike(int userId, int bikeId, int stationId) {
	// TODO Auto-generated method stub
	return false;
    }

}
