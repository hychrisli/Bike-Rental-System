package cmpe282.station.entity;

import java.util.Date;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class InBike{

    @PrimaryKeyColumn(name="txn_id", type = PrimaryKeyType.PARTITIONED)
    private String txnId;
    
    @Column(value="bike_id")
    private String bikeId;
    
    @Column(value="user_id")
    private String userId;
    
    @Column(value="from_station_id")
    private String fromStationId;
    
    @Column(value="checkout_time")
    private Date checkoutTime;
    
    @Column(value="to_station_id")
    private String toStationId;
    
    @Column(value="checkin_time")
    private Date checkinTime;

    @Column(value="grand_total")
    private Float grandTotal;
    
    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public Date getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Date checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public Date getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Date checkinTime) {
        this.checkinTime = checkinTime;
    }

    public String getFromStationId() {
        return fromStationId;
    }

    public String getToStationId() {
        return toStationId;
    }

    public void setFromStationId(String fromStationId) {
        this.fromStationId = fromStationId;
    }

    public void setToStationId(String toStationId) {
        this.toStationId = toStationId;
    }

    public String getUserId() {
        return userId;
    }

    public Float getGrandTotal() {
        return grandTotal;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setGrandTotal(Float grandTotal) {
        this.grandTotal = grandTotal;
    }
}
