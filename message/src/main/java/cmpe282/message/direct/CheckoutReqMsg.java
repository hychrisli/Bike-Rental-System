package cmpe282.message.direct;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckoutReqMsg {

    @JsonProperty("user_id")
    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    
}
