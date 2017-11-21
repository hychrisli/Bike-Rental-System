package cmpe282.message.direct;

public class CheckinReqMsg {

    String bike_id;
    
    String station_id;

    public String getBikeId() {
        return bike_id;
    }

    public String getStationId() {
        return station_id;
    }

    public void setBikeId(String bikeId) {
        this.bike_id = bikeId;
    }

    public void setStationId(String stationId) {
        this.station_id = stationId;
    }
    
    
}
