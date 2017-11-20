package cmpe282.station.service;

import cmpe282.station.entity.Bike;

public interface BikeService {

    public Bike getBike(String bikeId);
    
    public Bike reserveBike(String stationId, String txnId, String userId);
    
    public boolean checkoutBike(String userId, String bikeId, String stationId);
    
    public boolean checkinBike(String userId, String bikeId, String stationId);
}
