package cmpe282.message.mq;

public class ComplMsg {

    private String transaction_id;
    
    private String user_id;
    
    private float grand_total;

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

    public float getGrandTotal() {
        return grand_total;
    }

    public void setGrandTotal(float grand_total) {
        this.grand_total = grand_total;
    }
    
    
}
