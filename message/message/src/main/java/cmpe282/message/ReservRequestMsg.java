package cmpe282.message;

public class ReservRequestMsg {

    private String transaction_id;
    private String user_id;
    private int station_id;
    
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
    public int getStation_id() {
        return station_id;
    }
    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }
    
    
}
