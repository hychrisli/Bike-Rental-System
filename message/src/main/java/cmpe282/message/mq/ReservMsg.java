package cmpe282.message.mq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservMsg {
    
    @JsonProperty("transaction_id")
    private String txnId;
    
    @JsonProperty("user_id")
    private String userId;
    
    @JsonProperty("station_id")
    private String stationId;

    public String getTxnId() {
        return txnId;
    }

    public String getUserId() {
        return userId;
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

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
    
}
