package cmpe282.message.direct;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckoutReqMsg {

    @JsonProperty("user_id")
    String userId;
    
    @JsonProperty("station_id")
    String stationId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
    
    
    
}
