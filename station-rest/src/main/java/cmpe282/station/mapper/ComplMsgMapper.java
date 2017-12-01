package cmpe282.station.mapper;

import cmpe282.message.direct.CheckinConfirmMsg;
import cmpe282.message.mq.ComplMsg;
import cmpe282.station.entity.InBike;

public class ComplMsgMapper {

    public static CheckinConfirmMsg toOkCheckinMsg(ComplMsg cmplMsg, String messageId){
	
	CheckinConfirmMsg checkinMsg = new CheckinConfirmMsg();
	checkinMsg.setCheckedIn(true);
	checkinMsg.setUserId(cmplMsg.getUserId());
	checkinMsg.setTxnId(cmplMsg.getTxnId());
	checkinMsg.setMessageId(messageId);
	checkinMsg.setGrandTotal(cmplMsg.getGrandTotal());
	checkinMsg.setStatusDetails("Successfully Checkin In");
	
	return checkinMsg;
    }
    
    public static CheckinConfirmMsg toNotOkCheckinMsg(String details) {
	
	CheckinConfirmMsg checkinMsg = new CheckinConfirmMsg();
	checkinMsg.setCheckedIn(false);
	checkinMsg.setStatusDetails(details);
	
	return checkinMsg;
    }
    
    public static ComplMsg toComplMsg (InBike inBike) {
	
	ComplMsg complMsg = new ComplMsg();
	complMsg.setTxnId(inBike.getTxnId());
	complMsg.setUserId(inBike.getUserId());
	complMsg.setGrandTotal(inBike.getGrandTotal());
	
	return complMsg;
    }
    
}
