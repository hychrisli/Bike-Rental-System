package cmpe282.station.mapper;

import cmpe282.message.direct.CheckinConfirmMsg;
import cmpe282.message.mq.ComplMsg;
import cmpe282.station.entity.InBike;

public class ComplMsgMapper {

    public static CheckinConfirmMsg toOkCheckinMsg(InBike inBike){
	
	CheckinConfirmMsg checkinMsg = new CheckinConfirmMsg();
	checkinMsg.setCheckedIn(true);
	checkinMsg.setUserId(inBike.getUserId());
	checkinMsg.setTransactionId(inBike.getTxnId());
	checkinMsg.setGrandTotal(
		FareCalculator.calcFare(
			inBike.getCheckoutTime(), 
			inBike.getCheckinTime()));
	
	return checkinMsg;
    }
    
    public static CheckinConfirmMsg toNotOkCheckinMsg() {
	
	CheckinConfirmMsg checkinMsg = new CheckinConfirmMsg();
	checkinMsg.setCheckedIn(false);
	
	return checkinMsg;
    }
    
    public static ComplMsg toComplMsg (CheckinConfirmMsg checkinMsg) {
	
	ComplMsg complMsg = new ComplMsg();
	complMsg.setTransactionId(checkinMsg.getTransactionId());
	complMsg.setUserId(checkinMsg.getUserId());
	complMsg.setGrandTotal(checkinMsg.getGrandTotal());
	
	return complMsg;
    }
    
}
