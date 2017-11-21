package cmpe282.station.service.test;

import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.times;

import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;

import cmpe282.station.entity.OutBike;
import cmpe282.station.entity.RsvdBike;
import cmpe282.station.mapper.MapIdMapper;
import cmpe282.station.repository.InBikeRepository;
import cmpe282.station.repository.OutBikeRepository;
import cmpe282.station.repository.RsvdBikeRepository;
import cmpe282.station.repository.StationBikeRepository;
import cmpe282.station.service.BikeService;
import cmpe282.station.service.impl.BikeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class BikeServiceTest extends AbstractStationServiceTest {

    private static Logger LOGGER = Logger.getLogger(BikeServiceTest.class.getName());
    
    @Mock
    private StationBikeRepository stationBikeRepo;
    
    @Mock
    private RsvdBikeRepository rsvdBikeRepo;
    
    @Mock
    private OutBikeRepository outBikeRepo;
    
    @Mock
    private InBikeRepository inBikeRepo;
    
    @InjectMocks
    private BikeService bikeSvc = new BikeServiceImpl();
    
    @Test
    public void testReserveBike(){
	
	Mockito.when(stationBikeRepo.findOneBike(station1.getStationId())).thenReturn(bike1);

	RsvdBike rsvdBike = bikeSvc.rsvBike(station1.getStationId(), txnId1, userId1);
	
	Assert.assertThat(rsvdBike, new ReflectionEquals(bike1Rsvd));
	Mockito.verify(stationBikeRepo, times(1)).findOneBike(station1.getStationId());
	
	rsvdBike = bikeSvc.rsvBike(station0.getStationId(), txnId1, userId1);
	Assert.assertEquals(null, rsvdBike);
    }
    
    @Test
    public void testCheckOutBike(){
	Mockito
	.when(rsvdBikeRepo.findOne(refEq(MapIdMapper.toMapId("userId", userId1))))
	.thenReturn(bike1Rsvd);
	
	OutBike outBike = bikeSvc.checkoutBike(userId1);
	outBike.setCheckoutTime(checkoutTime);
	Assert.assertThat(outBike, new ReflectionEquals(bike1Out));
	
	Mockito
	.when(rsvdBikeRepo.findOne(refEq(MapIdMapper.toMapId("userId", userId2))))
	.thenReturn(null);
	
	outBike = bikeSvc.checkoutBike(userId2);
	Assert.assertEquals(outBike, null);

    }
}
