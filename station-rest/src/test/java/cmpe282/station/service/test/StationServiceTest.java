package cmpe282.station.service.test;

import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.times;

import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.cassandra.repository.MapId;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cmpe282.message.direct.CheckoutConfirmMsg;
import cmpe282.message.direct.CheckoutReqMsg;
import cmpe282.message.mq.ConfirmMsg;
import cmpe282.message.mq.ReservMsg;
import cmpe282.station.mapper.CheckoutMsgMapper;
import cmpe282.station.mapper.ConfirmMsgMapper;
import cmpe282.station.mapper.MapIdMapper;
import cmpe282.station.repository.StationRepository;
import cmpe282.station.service.BikeService;
import cmpe282.station.service.StationService;
import cmpe282.station.service.impl.StationServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class StationServiceTest extends AbstractStationServiceTest {

    private static Logger LOGGER = Logger.getLogger(StationServiceTest.class.getName());
    
    @Mock
    private StationRepository stationRepo;
    
    @Mock
    private BikeService bikeSvc;
    
    @InjectMocks
    private StationService stationSvc = new StationServiceImpl();
    
    private ReservMsg reservOkMsg, reservFailMsgNoBike;
    private ConfirmMsg confirmOkMsg, confirmFailMsgNoBike;
    private CheckoutReqMsg ckReqOkmsg, ckReqFailMsg;
    private CheckoutConfirmMsg ckConfirmOkMsg, ckConfirmFailMsg;
    
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
	
	
	ckReqOkmsg = new CheckoutReqMsg();
	ckReqOkmsg.setUserId(userId1);
	
	ckReqFailMsg = new CheckoutReqMsg();
	ckReqFailMsg.setUserId(userId2);
	
	ckConfirmOkMsg = CheckoutMsgMapper.toOkMsg(bike1Out);
	ckConfirmFailMsg = CheckoutMsgMapper.toNotOkMsg();
    }
    
    
    @Test
    public void testGetStation(){
	Assert.assertEquals(stationSvc.getStation(station1.getStationId()), station1);
	Assert.assertEquals(stationSvc.getStation(station2.getStationId()), null);
    }
    
    @Test
    public void testReserveOneBike() throws JsonProcessingException{
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
	
	Assert.assertThat(stationSvc.checkoutOneBike(ckReqOkmsg), 
		new ReflectionEquals(ckConfirmOkMsg));
	
	Assert.assertThat(stationSvc.checkoutOneBike(ckReqFailMsg), 
		new ReflectionEquals(ckConfirmFailMsg));
    }
    
}
