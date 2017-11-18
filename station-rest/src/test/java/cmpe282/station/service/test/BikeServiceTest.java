package cmpe282.station.service.test;

import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.times;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.cassandra.repository.MapId;

import cmpe282.station.entity.Bike;
import cmpe282.station.mapper.MapIdMapper;
import cmpe282.station.repository.BikeRepository;
import cmpe282.station.service.BikeService;
import cmpe282.station.service.impl.BikeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class BikeServiceTest extends AbstractStationServiceTest {

    @Mock
    private BikeRepository bikeRepo;
    
    @InjectMocks
    private BikeService bikeSvc = new BikeServiceImpl();
    
    @Test
    public void testReserveBike(){
	
	MapId mId1 = MapIdMapper.toMapId("stationId", station1.getStationId());
	mId1.put("isReserved", false);
	Mockito.when(bikeRepo.findOneBike(station1.getStationId())).thenReturn(bike1);

	Bike rsvdBike = bikeSvc.reserveBike(station1.getStationId(), txnId1, userId1);
	
	Assert.assertThat(rsvdBike, new ReflectionEquals(bike1Rsvd));
	Mockito.verify(bikeRepo, times(1)).findOneBike(station1.getStationId());
	
	rsvdBike = bikeSvc.reserveBike(station0.getStationId(), txnId1, userId1);
	Assert.assertEquals(null, rsvdBike);
    }
}
