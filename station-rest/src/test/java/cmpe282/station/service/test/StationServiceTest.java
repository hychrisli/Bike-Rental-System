package cmpe282.station.service.test;

import static cmpe282.message.Topics.TOPIC_COMPLETION;
import static org.mockito.Matchers.refEq;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.MapId;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.api.core.ApiFuture;

import cmpe282.message.direct.CheckinConfirmMsg;
import cmpe282.message.direct.CheckinReqMsg;
import cmpe282.message.direct.CheckoutConfirmMsg;
import cmpe282.message.direct.CheckoutReqMsg;
import cmpe282.message.mq.ComplMsg;
import cmpe282.message.mq.ConfirmMsg;
import cmpe282.message.mq.ReservMsg;
import cmpe282.station.mapper.CheckoutMsgMapper;
import cmpe282.station.mapper.ComplMsgMapper;
import cmpe282.station.mapper.ConfirmMsgMapper;
import cmpe282.station.mapper.MapIdMapper;
import cmpe282.station.repository.StationRepository;
import cmpe282.station.service.BikeService;
import cmpe282.station.service.PublisherService;
import cmpe282.station.service.StationService;
import cmpe282.station.service.impl.StationServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class StationServiceTest extends AbstractStationServiceTest {
    
    @Mock
    private StationRepository stationRepo;
    
    @Mock
    private BikeService bikeSvc;
    
    @Mock
    private PublisherService pubSvc;
    
    @InjectMocks
    private StationService stationSvc = new StationServiceImpl();
    
    private ReservMsg reservOkMsg, reservFailMsgNoBike;
    private ConfirmMsg confirmOkMsg, confirmFailMsgNoBike;
    private CheckoutReqMsg checkoutReqOkmsg, checkoutReqFailMsg;
    private CheckoutConfirmMsg checkoutConfirmOkMsg, checkoutConfirmFailMsg;
    private CheckinReqMsg checkinReqOkmsg, checkinReqFailMsg;
    private CheckinConfirmMsg checkinConfirmOkMsg, checkinConfirmFailMsg;
    private ComplMsg complMsg;
    
    
    @Before
    public void init(){
	
	initMsgs();
	
	MapId stationMapId0 = MapIdMapper.toMapId("stationId", confirmFailMsgNoBike.getStationId());
	Mockito.when(stationRepo.findOne(refEq(stationMapId0))).thenReturn(station0);

	MapId stationMapId1 = MapIdMapper.toMapId("stationId", confirmOkMsg.getStationId());
	Mockito.when(stationRepo.findOne(refEq(stationMapId1))).thenReturn(station1);
    }
    
    private void initMsgs(){
	
	reservOkMsg = new ReservMsg();
	reservOkMsg.setStationId(station1.getStationId());
	reservOkMsg.setTransactionId(txnId1);
	reservOkMsg.setUserId(userId1);
	
	reservFailMsgNoBike = new ReservMsg();
	reservFailMsgNoBike.setStationId(station0.getStationId());
	reservFailMsgNoBike.setTransactionId(txnId1);
	reservFailMsgNoBike.setUserId(userId1);
	
	confirmOkMsg = ConfirmMsgMapper.toOkMsg(bike1Rsvd);
	confirmFailMsgNoBike = ConfirmMsgMapper.toNotOkMsg(reservFailMsgNoBike);
	
	
	checkoutReqOkmsg = new CheckoutReqMsg();
	checkoutReqOkmsg.setUserId(userId1);
	
	checkoutReqFailMsg = new CheckoutReqMsg();
	checkoutReqFailMsg.setUserId(userId2);
	
	checkoutConfirmOkMsg = CheckoutMsgMapper.toOkMsg(bike1Out);
	checkoutConfirmFailMsg = CheckoutMsgMapper.toNotOkMsg();
	
	checkinReqOkmsg = new CheckinReqMsg();
	checkinReqOkmsg.setBikeId(bike1In.getBikeId());
	checkinReqOkmsg.setStationId(bike1In.getToStationId());
	
	checkinReqFailMsg = new CheckinReqMsg();
	checkinReqFailMsg.setBikeId(bike2.getBikeId());
	checkinReqFailMsg.setStationId(station1.getStationId());
	
	complMsg = ComplMsgMapper.toComplMsg(bike1In);
	complMsg.setGrandTotal(grandTotal);
	checkinConfirmOkMsg = ComplMsgMapper.toOkCheckinMsg(complMsg, "msg1");
	checkinConfirmFailMsg = ComplMsgMapper.toNotOkCheckinMsg();
    }
    
    
    @Test
    public void testGetStation(){
	Assert.assertEquals(stationSvc.getStation(station1.getStationId()), station1);
	Assert.assertEquals(stationSvc.getStation(station2.getStationId()), null);
    }
    
    @Test
    public void testReserveOneBike(){
	Mockito.when(bikeSvc.rsvBike(
		confirmFailMsgNoBike.getStationId(), 
		confirmFailMsgNoBike.getTransactionId(), 
		confirmFailMsgNoBike.getUserId())).thenReturn(null);
	
	Mockito.when(bikeSvc.rsvBike(
		confirmOkMsg.getStationId(), 
		confirmOkMsg.getTransactionId(),
		confirmOkMsg.getUserId())).thenReturn(bike1Rsvd);
	
	Assert.assertThat(stationSvc.reserveOneBike(reservOkMsg), 
		new ReflectionEquals(confirmOkMsg));
	Assert.assertThat(stationSvc.reserveOneBike(reservFailMsgNoBike), 
		new ReflectionEquals(confirmFailMsgNoBike));
    }
    
    @Test
    public void testCheckoutOneBike() {
	
	Mockito
	.when(bikeSvc.checkoutBike(userId1))
	.thenReturn(bike1Out);
	
	Mockito
	.when(bikeSvc.checkoutBike(userId2))
	.thenReturn(null);
	
	Assert.assertThat(stationSvc.checkoutOneBike(checkoutReqOkmsg), 
		new ReflectionEquals(checkoutConfirmOkMsg));
	
	Assert.assertThat(stationSvc.checkoutOneBike(checkoutReqFailMsg), 
		new ReflectionEquals(checkoutConfirmFailMsg));
    }
    
    @Test
    public void testCheckinOnBike() throws Exception{
	
	Mockito
	.when(bikeSvc.checkinBike(bike1In.getBikeId(), bike1In.getToStationId()))
	.thenReturn(bike1In);
	
	Mockito.when(bikeSvc.checkinBike(bike2.getBikeId(), station1.getStationId()))
	.thenReturn(null);
	
	Mockito.when(pubSvc.publishMessage(TOPIC_COMPLETION.name(), complMsg))
	.thenReturn("msg1");
	
	CheckinConfirmMsg cic1Msg = stationSvc.checkinOneBike(checkinReqOkmsg);
	cic1Msg.setMessageId("msg1");
	cic1Msg.setGrandTotal(grandTotal);
	Assert.assertThat(cic1Msg, 
		new ReflectionEquals(checkinConfirmOkMsg));
	
	Assert.assertThat(stationSvc.checkinOneBike(checkinReqFailMsg), 
		new ReflectionEquals(checkinConfirmFailMsg));
	
    }
    
}
