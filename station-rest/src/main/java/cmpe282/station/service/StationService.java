package cmpe282.station.service;

import cmpe282.message.direct.CheckoutConfirmMsg;
import cmpe282.message.direct.CheckoutReqMsg;
import cmpe282.message.mq.ComplMsg;
import cmpe282.message.mq.ConfirmMsg;
import cmpe282.message.mq.ReservMsg;
import cmpe282.station.entity.Station;

public interface StationService {
    
    public Station getStation(String stationId);
    
    public boolean updateAvailBikes(String stationId, int delta);
    
    public boolean increaseAvailBikesByOne(String stationId);
    
    public boolean decreaseAvailBikesByOne(String stationId);
    
    public ConfirmMsg reserveOneBike(ReservMsg reservMsg);
    
    public CheckoutConfirmMsg checkoutOneBike(CheckoutReqMsg checkoutMsg);
    
    public ComplMsg checkinOneBike(String stationId, String bikeId);
    

}
