package cmpe282.message.mq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfirmMsg {

    @JsonProperty("transaction_id")
    private String txnId;
    
    @JsonProperty("user_id")
    private String userId;
    
    @JsonProperty("is_reserved")
    private boolean isReserved;
    
    @JsonProperty("bike_id")
    private String bikeId;
    
    @JsonProperty("station_id")
    private String stationId;

    public String getTxnId() {
        return txnId;
    }

    public String getUserId() {
        return userId;
    }

    @JsonProperty("is_reserved")
    public boolean isReserved() {
        return isReserved;
    }

    public String getBikeId() {
        return bikeId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
    
}
