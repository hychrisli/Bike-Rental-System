package cmpe282.message;

public class ConfirmMsg {

    private String transaction_id;
    
    private String user_id;
    
    private boolean is_reserved;
    
    private String bike_id;
    
    private String station_id;

    public String getTransactionId() {
        return transaction_id;
    }

    public void setTransactionId(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public boolean isReserved() {
        return is_reserved;
    }

    public void setReserved(boolean is_reserved) {
        this.is_reserved = is_reserved;
    }

    public String getBikeId() {
        return bike_id;
    }

    public void setBikeId(String bike_id) {
        this.bike_id = bike_id;
    }

    public String getStationId() {
        return station_id;
    }

    public void setStationId(String station_id) {
        this.station_id = station_id;
    }
    
    
}
