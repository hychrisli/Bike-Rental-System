package cmpe282.station.mapper;

import cmpe282.message.direct.CheckinConfirmMsg;
import cmpe282.message.mq.ComplMsg;
import cmpe282.station.entity.InBike;

public class ComplMsgMapper {

    public static CheckinConfirmMsg toOkCheckinMsg(ComplMsg cmplMsg, String messageId){
	
	CheckinConfirmMsg checkinMsg = new CheckinConfirmMsg();
	checkinMsg.setCheckedIn(true);
	checkinMsg.setUserId(cmplMsg.getUserId());
	checkinMsg.setTransactionId(cmplMsg.getTransactionId());
	checkinMsg.setMessageId(messageId);
	checkinMsg.setGrandTotal(cmplMsg.getGrandTotal());
	
	return checkinMsg;
    }
    
    public static CheckinConfirmMsg toNotOkCheckinMsg() {
	
	CheckinConfirmMsg checkinMsg = new CheckinConfirmMsg();
	checkinMsg.setCheckedIn(false);
	
	return checkinMsg;
    }
    
    public static ComplMsg toComplMsg (InBike inBike) {
	
	ComplMsg complMsg = new ComplMsg();
	complMsg.setTransactionId(inBike.getTxnId());
	complMsg.setUserId(inBike.getUserId());
	complMsg.setGrandTotal(
		FareCalculator.calcFare(
			inBike.getCheckoutTime(), 
			inBike.getCheckinTime()));
	
	return complMsg;
    }
    
}
