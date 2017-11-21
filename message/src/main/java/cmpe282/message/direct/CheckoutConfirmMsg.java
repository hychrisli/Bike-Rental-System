package cmpe282.message.direct;

public class CheckoutConfirmMsg {

    boolean is_success;
    
    String bike_id;

    public boolean isSuccess() {
        return is_success;
    }

    public String getBikeId() {
        return bike_id;
    }

    public void setSuccess(boolean isSuccess) {
        this.is_success = isSuccess;
    }

    public void setBikeId(String bikeId) {
        this.bike_id = bikeId;
    } 
    
    
    
}
