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

import cmpe282.message.ConfirmMsg;
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
    
    @Before
    public void init(){
	
	MapId stationMapId0 = MapIdMapper.toMapId("stationId", confirmFailMsgNoBike.getStationId());
	Mockito.when(stationRepo.findOne(refEq(stationMapId0))).thenReturn(station0);
	Mockito.when(bikeSvc.reserveBike(
		confirmFailMsgNoBike.getStationId(), 
		confirmFailMsgNoBike.getTransactionId(), 
		confirmFailMsgNoBike.getUserId())).thenReturn(null);
	
	MapId stationMapId1 = MapIdMapper.toMapId("stationId", confirmOkMsg.getStationId());
	Mockito.when(stationRepo.findOne(refEq(stationMapId1))).thenReturn(station1);
	Mockito.when(bikeSvc.reserveBike(
		confirmOkMsg.getStationId(), 
		confirmOkMsg.getTransactionId(),
		confirmOkMsg.getUserId())).thenReturn(bike1Rsvd);
    }
    
    
    @Test
    public void testGetStation(){
	Assert.assertEquals(stationSvc.getStation(station1.getStationId()), station1);
	Assert.assertEquals(stationSvc.getStation(station2.getStationId()), null);
    }
    
    @Test
    public void testReserveOneBike() throws JsonProcessingException{
	
	Assert.assertThat(stationSvc.reserveOneBike(reservOkMsg), new ReflectionEquals(confirmOkMsg));
	Assert.assertThat(stationSvc.reserveOneBike(reservFailMsgNoBike), new ReflectionEquals(confirmFailMsgNoBike));
    }
    
}
