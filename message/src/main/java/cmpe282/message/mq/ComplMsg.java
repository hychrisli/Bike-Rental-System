package cmpe282.message.mq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComplMsg {

    @JsonProperty("transaction_id")
    private String txnId;
    
    @JsonProperty("user_id")
    private String userId;
    
    @JsonProperty("grand_total")
    private float grandTotal;

    public String getUserId() {
        return userId;
    }

    public float getGrandTotal() {
        return grandTotal;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setGrandTotal(float grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    
}
