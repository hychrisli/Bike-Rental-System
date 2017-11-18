package cmpe282.station.entity;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Bike{
    
    @PrimaryKeyColumn(name="bike_id",  ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private int bikeId;
    
    @PrimaryKeyColumn(name = "station_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private int stationId;
    
    @Column(value="is_reserved")
    private boolean isReserved;
    
    @Column(value="txn_id")
    private String txnId;
    
    @Column(value="user_id")
    private String userId;

    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}
