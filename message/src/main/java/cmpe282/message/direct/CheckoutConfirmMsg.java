package cmpe282.message.direct;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckoutConfirmMsg {

    @JsonProperty("is_success")
    boolean isSuccess;
    
    @JsonProperty("bike_id")
    String bikeId;
    
    @JsonProperty("status_details")
    String statusDetails;

    @JsonProperty("is_success")
    public boolean isSuccess() {
        return isSuccess;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public String getStatusDetails() {
        return statusDetails;
    }

    public void setStatusDetails(String statusDetails) {
        this.statusDetails = statusDetails;
    }
}
