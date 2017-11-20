package cmpe282.station.service;

import cmpe282.message.ComplMsg;
import cmpe282.message.ConfirmMsg;
import cmpe282.message.ReservMsg;
import cmpe282.station.entity.Station;

public interface StationService {
    
    public Station getStation(String stationId);
    
    public boolean updateAvailBikes(String stationId, int delta);
    
    public boolean increaseAvailBikesByOne(String stationId);
    
    public boolean decreaseAvailBikesByOne(String stationId);
    
    public ConfirmMsg reserveOneBike(ReservMsg reservMsg);
    
    public boolean checkoutOneBike(String stationId, String bikeId, String userId);
    
    public ComplMsg checkinOneBike(String stationId, String bikeId, String userId);
    

}
