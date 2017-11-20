package cmpe282.station.mapper;

import cmpe282.message.ConfirmMsg;
import cmpe282.message.ReservMsg;
import cmpe282.station.entity.Bike;

public class ConfirmMsgMapper {

    public static ConfirmMsg toOkMsg(Bike bike){
	
	ConfirmMsg msg = new ConfirmMsg();
	msg.setStationId(bike.getStationId());
	msg.setBikeId(bike.getBikeId());
	msg.setReserved(true);
	msg.setUserId(bike.getUserId());
	msg.setTransactionId(bike.getTxnId());
	
	return msg;
    }
    
    public static ConfirmMsg toNotOkMsg(ReservMsg reservMsg){
	
	ConfirmMsg msg = new ConfirmMsg();
	msg.setStationId(reservMsg.getStationId());
	msg.setTransactionId(reservMsg.getTransactionId());
	msg.setUserId(reservMsg.getUserId());
	msg.setReserved(false);
	msg.setBikeId("");
	
	return msg;
    }
    
}
