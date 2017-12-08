package cmpe282.pubsub.msg;

public class HelloStation {

    private int num;
    private String msg;
    
    public HelloStation(int num, String msg){
	this.num = num;
	this.msg = msg;
    }
    
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
