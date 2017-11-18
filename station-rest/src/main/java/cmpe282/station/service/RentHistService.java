package cmpe282.station.service;

import cmpe282.station.entity.Bike;
import cmpe282.station.entity.RentHist;

public interface RentHistService {

    public RentHist getRentHist(String txnId);
    
    public RentHist addBikeCheckout(Bike bike, int StationId);
    
    public RentHist updateBikeCheckin(int bikeId, int stationId, String userId);
}
