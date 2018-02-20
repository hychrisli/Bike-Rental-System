package cmpe282.station.service.test;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import cmpe282.station.mapper.FareCalculator;

@RunWith(MockitoJUnitRunner.class)
public class FareCalculatorTest {

    @Test
    public void testFare1 () {
	Date checkoutTs = Timestamp.valueOf("2017-11-20 19:51:31");
	Date checkinTs = Timestamp.valueOf("2017-11-20 20:21:17");
	Float grandTotal = FareCalculator.calcFare(checkoutTs, checkinTs);
	Assert.assertEquals(6.0f, grandTotal, 0.01f);
    }
    
    
    @Test
    public void testFare2 () {
	Date checkoutTs = Timestamp.valueOf("2017-11-20 19:51:31");
	Date checkinTs = Timestamp.valueOf("2017-11-20 20:42:17");
	Float grandTotal = FareCalculator.calcFare(checkoutTs, checkinTs);
	Assert.assertEquals(11.0f, grandTotal, 0.01f);
    }
    
    
    @Test
    public void testFare3 () {
	Date checkoutTs = Timestamp.valueOf("2017-11-20 19:51:31");
	Date checkinTs = Timestamp.valueOf("2017-11-20 20:33:17");
	Float grandTotal = FareCalculator.calcFare(checkoutTs, checkinTs);
	Assert.assertEquals(8.5f, grandTotal, 0.01f);
    }
    
    @Test
    // 29 MIN 59 sec -> BASE FARE
    public void testFare4 () {
	Date checkoutTs = Timestamp.valueOf("2017-11-20 19:51:31");
	Date checkinTs = Timestamp.valueOf("2017-11-20 20:51:30");
	Float grandTotal = FareCalculator.calcFare(checkoutTs, checkinTs);
	Assert.assertEquals(11.0f, grandTotal, 0.01f);
    }
    
    @Test
    // 30 min -> BASE_FARE
    public void testFare5 () {
	Date checkoutTs = Timestamp.valueOf("2017-11-20 19:51:31");
	Date checkinTs = Timestamp.valueOf("2017-11-20 20:21:31");
	Float grandTotal = FareCalculator.calcFare(checkoutTs, checkinTs);
	Assert.assertEquals(6.0f, grandTotal, 0.01f);
    }
    
    @Test
    // 30 min 1 sec -> BASE_FARE
    public void testFare6 () {
	Date checkoutTs = Timestamp.valueOf("2017-11-20 19:51:31");
	Date checkinTs = Timestamp.valueOf("2017-11-20 20:21:32");
	Float grandTotal = FareCalculator.calcFare(checkoutTs, checkinTs);
	Assert.assertEquals(6.0f, grandTotal, 0.01f);
    }
    
    
    @Test
    // 30 min 59 sec -> BASE_FARE
    public void testFare7 () {
	Date checkoutTs = Timestamp.valueOf("2017-11-20 19:51:31");
	Date checkinTs = Timestamp.valueOf("2017-11-20 20:22:30");
	Float grandTotal = FareCalculator.calcFare(checkoutTs, checkinTs);
	Assert.assertEquals(6.0f, grandTotal, 0.01f);
    }
    
    @Test
    // 31 min -> BASE_FARE + INCR_FARE
    public void testFare8 () {
	Date checkoutTs = Timestamp.valueOf("2017-11-20 19:51:31");
	Date checkinTs = Timestamp.valueOf("2017-11-20 20:22:31");
	Float grandTotal = FareCalculator.calcFare(checkoutTs, checkinTs);
	Assert.assertEquals(8.5f, grandTotal, 0.01f);
    }
    
    
    @Test
    // 45 min 59 sec -> BASE_FARE + INCR_FARE
    public void testFare9 () {
	Date checkoutTs = Timestamp.valueOf("2017-11-20 19:51:31");
	Date checkinTs = Timestamp.valueOf("2017-11-20 20:37:30");
	Float grandTotal = FareCalculator.calcFare(checkoutTs, checkinTs);
	Assert.assertEquals(8.5f, grandTotal, 0.01f);
    }
    
    @Test
    // 46 min -> BASE_FARE + INCR_FARE * 2
    public void testFare10 () {
	Date checkoutTs = Timestamp.valueOf("2017-11-20 19:51:31");
	Date checkinTs = Timestamp.valueOf("2017-11-20 20:37:31");
	Float grandTotal = FareCalculator.calcFare(checkoutTs, checkinTs);
	Assert.assertEquals(11.0f, grandTotal, 0.01f);
    }
    
}
