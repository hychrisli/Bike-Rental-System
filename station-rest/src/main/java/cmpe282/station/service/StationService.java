package cmpe282.station.service;

import cmpe282.message.ComplMsg;
import cmpe282.message.ConfirmMsg;
import cmpe282.station.entity.Station;

public interface StationService {
    
    public Station getStation(int stationId);
    
    public void updateAvailBikes(int stationId, int availBikes);
    
    public void increaseAvailBikesByOne(int stationId);
    
    public void decreaseAvailBikesByOne(int stationId);
    
    public ConfirmMsg reserveOneBike(int stationId, String txnId, String userId);
    
    public boolean checkoutOneBike(int stationId, int bikeId, String userId);
    
    public ComplMsg checkinOneBike(int stationId, int bikeId, String userId);
    

}
