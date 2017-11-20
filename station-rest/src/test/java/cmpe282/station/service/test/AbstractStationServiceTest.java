package cmpe282.station.service.test;

import org.junit.Before;

import cmpe282.message.ConfirmMsg;
import cmpe282.message.ReservMsg;
import cmpe282.station.entity.Bike;
import cmpe282.station.entity.Station;

public class AbstractStationServiceTest {

    protected Station station0, station1, station2;
    protected String userId1, userId2;
    protected String txnId1, txnId2;
    protected Bike bike1, bike1Rsvd, bike2;
    protected ReservMsg reservOkMsg, reservFailMsgNoBike;
    protected ConfirmMsg confirmOkMsg, confirmFailMsgNoBike;
    
    
    @Before
    public void abstrInit(){
	userId1 = "user-want-bike1";
	userId2 = "user2";
	txnId1 = "reserve-bike1";
	txnId2 = "txn2";
	
	initStations();
	initBikes();
	initReservMsgs();
	initConfirmMsgs();
	
    }
    
    private void initStations(){

	station0 = new Station();
	station0.setStationId("s010");
	station0.setName("No Bike Station");
	station0.setTotalDocks(2);
	station0.setAvailBikes(0);
	
	station1 = new Station();
	station1.setStationId("s011");
	station1.setName("One Bike Station");
	station1.setTotalDocks(4);
	station1.setAvailBikes(1);
	
	station2 = new Station();
	station2.setStationId("s12");
	station2.setName("Two Bike Station");
	station2.setTotalDocks(3);
	station2.setAvailBikes(2);
	
    }
    
    
    private void initBikes(){
	
	bike1 = new Bike();
	bike1.setBikeId("b0001");
	bike1.setStationId(station1.getStationId());
	bike1.setReserved(false);
	
	
	bike1Rsvd = new Bike();
	bike1Rsvd.setBikeId(bike1.getBikeId());
	bike1Rsvd.setStationId(bike1.getStationId());
	bike1Rsvd.setReserved(true);
	bike1Rsvd.setTxnId(txnId1);
	bike1Rsvd.setUserId(userId1);
	
	
	bike2 = new Bike();
	bike2.setBikeId("b0002");
	bike2.setStationId(station2.getStationId());
	bike2.setReserved(false);
    }
    
    private void initReservMsgs(){
	
	reservOkMsg = new ReservMsg();
	reservOkMsg.setStationId(station1.getStationId());
	reservOkMsg.setTransactionId(txnId1);
	reservOkMsg.setUserId(userId1);
	
	reservFailMsgNoBike = new ReservMsg();
	reservFailMsgNoBike.setStationId(station0.getStationId());
	reservFailMsgNoBike.setTransactionId(txnId1);
	reservFailMsgNoBike.setUserId(userId1);
	
    }
    
    private void initConfirmMsgs(){
	
	confirmOkMsg = new ConfirmMsg();
	confirmOkMsg.setStationId(station1.getStationId());
	confirmOkMsg.setTransactionId(txnId1);
	confirmOkMsg.setReserved(true);
	confirmOkMsg.setBikeId(bike1.getBikeId());
	confirmOkMsg.setUserId(userId1);
	
	confirmFailMsgNoBike = new ConfirmMsg();
	confirmFailMsgNoBike.setStationId(station0.getStationId());
	confirmFailMsgNoBike.setTransactionId(txnId1);
	confirmFailMsgNoBike.setReserved(false);
	confirmFailMsgNoBike.setBikeId("");
	confirmFailMsgNoBike.setUserId(userId1);
	
    }
    
    
}
