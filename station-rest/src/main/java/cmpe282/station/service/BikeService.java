package cmpe282.station.service;

import cmpe282.station.entity.OutBike;
import cmpe282.station.entity.InBike;
import cmpe282.station.entity.RsvdBike;
import cmpe282.station.entity.StationBike;

public interface BikeService {

    public StationBike getStationBike(String stationBikeId);
    
    public RsvdBike getRsvdBike(String userId);
    
    public OutBike getOutBike(String bikeId);
    
    public InBike getInBike (String txnId);
    
    public RsvdBike rsvBike(String stationId, String txnId, String userId);
    
    public StationBike cancelRsvdBike (String userId);
    
    public OutBike checkoutBike(String userId, String stationId);
    
    public InBike checkinBike(String bikeId, String stationId);
}
