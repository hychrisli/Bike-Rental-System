package cmpe282.station.mapper;

import cmpe282.message.mq.ConfirmMsg;
import cmpe282.message.mq.ReservMsg;
import cmpe282.station.entity.RsvdBike;

public class ConfirmMsgMapper {

    public static ConfirmMsg toOkMsg(RsvdBike bike){
	
	ConfirmMsg msg = new ConfirmMsg();
	msg.setStationId(bike.getStationId());
	msg.setBikeId(bike.getBikeId());
	msg.setReserved(true);
	msg.setUserId(bike.getUserId());
	msg.setTxnId(bike.getTxnId());
	
	return msg;
    }
    
    public static ConfirmMsg toNotOkMsg(ReservMsg reservMsg){
	
	ConfirmMsg msg = new ConfirmMsg();
	msg.setStationId(reservMsg.getStationId());
	msg.setTxnId(reservMsg.getTxnId());
	msg.setUserId(reservMsg.getUserId());
	msg.setReserved(false);
	msg.setBikeId("");
	
	return msg;
    }
    
}
