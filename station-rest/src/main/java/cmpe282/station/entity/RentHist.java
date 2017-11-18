package cmpe282.station.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class RentHist implements Serializable {
    
    private static final long serialVersionUID = -1751988850698368935L;

    private String txn_id;
    
    private int bike_id;
    
    private int from_station_id;
    
    private Timestamp checkout_time;
    
    private int to_station_id;
    
    private Timestamp checkin_time;

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public int getBike_id() {
        return bike_id;
    }

    public void setBike_id(int bike_id) {
        this.bike_id = bike_id;
    }

    public int getFrom_station_id() {
        return from_station_id;
    }

    public void setFrom_station_id(int from_station_id) {
        this.from_station_id = from_station_id;
    }

    public Timestamp getCheckout_time() {
        return checkout_time;
    }

    public void setCheckout_time(Timestamp checkout_time) {
        this.checkout_time = checkout_time;
    }

    public int getTo_station_id() {
        return to_station_id;
    }

    public void setTo_station_id(int to_station_id) {
        this.to_station_id = to_station_id;
    }

    public Timestamp getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(Timestamp checkin_time) {
        this.checkin_time = checkin_time;
    }
    
    
    
}
