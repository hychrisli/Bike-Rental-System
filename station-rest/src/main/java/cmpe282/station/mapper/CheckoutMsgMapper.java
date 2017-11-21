package cmpe282.station.mapper;

import cmpe282.message.direct.CheckoutConfirmMsg;
import cmpe282.station.entity.OutBike;

public class CheckoutMsgMapper {

    public static CheckoutConfirmMsg toOkMsg(OutBike outBike){
	
	CheckoutConfirmMsg msg = new CheckoutConfirmMsg();
	msg.setSuccess(true);
	msg.setBikeId(outBike.getBikeId());
	
	return msg;
    }
    
    public static CheckoutConfirmMsg toNotOkMsg() {
	CheckoutConfirmMsg msg = new CheckoutConfirmMsg();
	msg.setSuccess(false);
	msg.setBikeId("");
	
	return msg;
    }
    
}
