package cmpe282.station.service;

import cmpe282.station.entity.Bike;

public interface BikeService {

    public Bike getBike(int bikeId);
    
    public Bike reserveBike(int stationId, String txnId, String userId);
    
    public boolean checkoutBike(int userId, int bikeId, int stationId);
    
    public boolean checkinBike(int userId, int bikeId, int stationId);
}
