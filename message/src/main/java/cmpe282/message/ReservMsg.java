package cmpe282.message;

public class ReservMsg {
    
    private String transaction_id;
    private String user_id;
    private int station_id;
    
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
    public int getStationId() {
        return station_id;
    }
    public void setStationId(int station_id) {
        this.station_id = station_id;
    }
    
}
