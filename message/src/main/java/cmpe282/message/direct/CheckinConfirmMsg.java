package cmpe282.message.direct;

public class CheckinConfirmMsg {

    Boolean checked_in;
    
    String transaction_id;
    
    String user_id;
    
    String message_id;

    Float grand_total;

    public Boolean isCheckedIn() {
        return checked_in;
    }

    public String getUserId() {
        return user_id;
    }

    public Float getGrandTotal() {
        return grand_total;
    }

    public void setCheckedIn(Boolean checkedIn) {
        this.checked_in = checkedIn;
    }

    public void setUserId(String userId) {
        this.user_id = userId;
    }

    public void setGrandTotal(Float grandTotal) {
        this.grand_total = grandTotal;
    }

    public String getTransactionId() {
        return transaction_id;
    }

    public void setTransactionId(String txnId) {
        this.transaction_id = txnId;
    }
    
    public String getMessageId() {
        return message_id;
    }

    public void setMessageId(String messageId) {
        this.message_id = messageId;
    }
}
