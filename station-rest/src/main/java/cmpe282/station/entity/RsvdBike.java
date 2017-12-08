package cmpe282.station.entity;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class RsvdBike {

    @PrimaryKeyColumn(name="user_id", type = PrimaryKeyType.PARTITIONED)
    private String userId;
    
    @Column(value = "bike_id")
    private String bikeId;
    
    @Column(value = "station_id")
    private String stationId;

    @Column(value = "txn_id")
    private String txnId;

    public String getUserId() {
        return userId;
    }

    public String getBikeId() {
        return bikeId;
    }

    public String getStationId() {
        return stationId;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }
    
    
    
}
