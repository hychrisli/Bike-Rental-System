package cmpe282.message.direct;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckinConfirmMsg {

    @JsonProperty("checked_in")
    boolean isCheckedIn;
    
    @JsonProperty("transaction_in")
    String txnId;
    
    @JsonProperty("user_id")
    String userId;
    
    @JsonProperty("message_id")
    String messageId;

    @JsonProperty("grand_total")
    Float grandTotal;


    public String getUserId() {
        return userId;
    }

    public String getMessageId() {
        return messageId;
    }

    public Float getGrandTotal() {
        return grandTotal;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setGrandTotal(Float grandTotal) {
        this.grandTotal = grandTotal;
    }

    @JsonProperty("checked_in")
    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public void setCheckedIn(boolean isCheckedIn) {
        this.isCheckedIn = isCheckedIn;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }
    
    
}
