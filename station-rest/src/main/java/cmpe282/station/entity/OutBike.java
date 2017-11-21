package cmpe282.station.entity;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class OutBike {

    @PrimaryKeyColumn(name = "bike_id", type = PrimaryKeyType.PARTITIONED)
    private String bikeId;
    
    @Column(value = "user_id")
    private String userId;
    
    @Column(value = "txn_id")
    private String txnId;
    
    @Column(value = "from_station_id")
    private String fromStationId;

    @Column(value="checkout_time")
    private Date checkoutTime;

    public String getBikeId() {
        return bikeId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTxnId() {
        return txnId;
    }

    public String getFromStationId() {
        return fromStationId;
    }

    public Date getCheckoutTime() {
        return checkoutTime;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public void setFromStationId(String fromStationId) {
        this.fromStationId = fromStationId;
    }

    public void setCheckoutTime(Date checkoutTime) {
        this.checkoutTime = checkoutTime;
    }
    
    
    

}
