package cmpe282.station.entity;


import java.sql.Timestamp;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class RentHist{

    @PrimaryKeyColumn(name="txn_id", type = PrimaryKeyType.PARTITIONED)
    private String txnId;
    
    @Column(value="bike_id")
    private String bikeId;
    
    @Column(value="from_station_id")
    private String fromStatioId;
    
    @Column(value="checkout_time")
    private Timestamp checkoutTime;
    
    @Column(value="to_station_id")
    private String toStationDd;
    
    @Column(value="checkin_time")
    private Timestamp checkinTime;

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

    public String getFromStatioId() {
        return fromStatioId;
    }

    public void setFromStatioId(String fromStatioId) {
        this.fromStatioId = fromStatioId;
    }

    public Timestamp getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Timestamp checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public String getToStationDd() {
        return toStationDd;
    }

    public void setToStationDd(String toStationDd) {
        this.toStationDd = toStationDd;
    }

    public Timestamp getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Timestamp checkinTime) {
        this.checkinTime = checkinTime;
    }

    
}
