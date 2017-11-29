package cmpe282.message.direct;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckinReqMsg {

    @JsonProperty("bike_id")
    String bikeId;
    
    @JsonProperty("station_id")
    String stationId;

    public String getBikeId() {
        return bikeId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
    
    
    
}
