package cmpe282.station.mapper;

import cmpe282.message.direct.CheckoutConfirmMsg;
import cmpe282.station.entity.OutBike;

public class CheckoutMsgMapper {

    public static CheckoutConfirmMsg toOkMsg(OutBike outBike){
	
	CheckoutConfirmMsg msg = new CheckoutConfirmMsg();
	msg.setSuccess(true);
	msg.setBikeId(outBike.getBikeId());
	msg.setStatusDetails("Sucessfully Checked Out");
	
	return msg;
    }
    
    public static CheckoutConfirmMsg toNotOkMsg(String details) {
	CheckoutConfirmMsg msg = new CheckoutConfirmMsg();
	msg.setSuccess(false);
	msg.setBikeId("");
	msg.setStatusDetails(details);
	
	return msg;
    }
    
}
