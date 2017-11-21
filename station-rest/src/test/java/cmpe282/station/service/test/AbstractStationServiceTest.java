package cmpe282.station.service.test;

import java.sql.Timestamp;

import org.junit.Before;

import cmpe282.station.entity.StationBike;
import cmpe282.station.mapper.BikeMapper;
import cmpe282.station.mapper.ConfirmMsgMapper;
import cmpe282.message.mq.ConfirmMsg;
import cmpe282.message.mq.ReservMsg;
import cmpe282.station.entity.InBike;
import cmpe282.station.entity.OutBike;
import cmpe282.station.entity.RsvdBike;
import cmpe282.station.entity.Station;

public class AbstractStationServiceTest {

    protected Station station0, station1, station2;
    protected String userId1, userId2;
    protected String txnId1, txnId2;
    protected StationBike bike1, bike2;
    protected RsvdBike bike1Rsvd;
    protected OutBike bike1Out;
    protected InBike bike1In;
    protected Timestamp checkoutTime, checkinTime;
    
    
    @Before
    public void abstrInit(){
	userId1 = "user-want-bike1";
	userId2 = "user2";
	txnId1 = "reserve-bike1";
	txnId2 = "txn2";
	checkoutTime = Timestamp.valueOf("2017-11-20 19:51:31");
	checkinTime = Timestamp.valueOf("2017-11-20 20:21:17");
	
	initStations();
	initBikes();
	
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
	station2.setStationId("s012");
	station2.setName("Two Bike Station");
	station2.setTotalDocks(3);
	station2.setAvailBikes(2);
	
    }
    
    
    private void initBikes(){
	
	bike1 = new StationBike();
	bike1.setBikeId("b0001");
	bike1.setStationId(station1.getStationId());

	bike1Rsvd = BikeMapper.toRsvdBike(bike1, userId1, txnId1);
	bike1Out = BikeMapper.toOutBike(bike1Rsvd);
	bike1Out.setCheckoutTime(checkoutTime);
	
	bike2 = new StationBike();
	bike2.setBikeId("b0002");
	bike2.setStationId(station2.getStationId());
    }
}
