package cmpe282.message;

import java.util.Date;

public class ReservResponseMsg {

    private String transaction_id;
    private String user_id;
    private boolean is_reserved = false;
    private Date timeout; 
    private int bike_id = 0;
    private int station_id = 0;
    public String getTransaction_id() {
        return transaction_id;
    }
    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public boolean isIs_reserved() {
        return is_reserved;
    }
    public void setIs_reserved(boolean is_reserved) {
        this.is_reserved = is_reserved;
    }
    public Date getTimeout() {
        return timeout;
    }
    public void setTimeout(Date timeout) {
        this.timeout = timeout;
    }
    public int getBike_id() {
        return bike_id;
    }
    public void setBike_id(int bike_id) {
        this.bike_id = bike_id;
    }
    public int getStation_id() {
        return station_id;
    }
    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }
    
    
    
}
