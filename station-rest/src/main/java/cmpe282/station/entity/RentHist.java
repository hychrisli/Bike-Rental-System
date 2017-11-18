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
    private int bikeId;
    
    @Column(value="from_station_id")
    private int fromStatioId;
    
    @Column(value="checkout_time")
    private Timestamp checkoutTime;
    
    @Column(value="to_station_id")
    private int toStationDd;
    
    @Column(value="checkin_time")
    private Timestamp checkinTime;

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public int getFromStatioId() {
        return fromStatioId;
    }

    public void setFromStatioId(int fromStatioId) {
        this.fromStatioId = fromStatioId;
    }

    public Timestamp getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Timestamp checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public int getToStationDd() {
        return toStationDd;
    }

    public void setToStationDd(int toStationDd) {
        this.toStationDd = toStationDd;
    }

    public Timestamp getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Timestamp checkinTime) {
        this.checkinTime = checkinTime;
    }

    
}
