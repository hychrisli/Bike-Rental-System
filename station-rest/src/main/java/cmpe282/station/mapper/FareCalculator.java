package cmpe282.station.mapper;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class FareCalculator {

    private static final float SVC_FEE = 2f;
    private static final int BASE_MINUTES = 30;
    private static final int INCR_MINUTES = 15;
    private static final float BASE_RATE = 4.0f;	// half hour
    private static final float INCR_RATE = 2.5f;	// every additional 15 minutes
    protected static Logger LOGGER = Logger.getLogger(FareCalculator.class.getName());
    
    public static Float calcFare (Date checkoutTs, Date checkinTs) {
	// LOGGER.info(checkoutTs.toString() + " --> " + checkoutTs.getTime());
	// LOGGER.info(checkinTs.toString() + " --> " + checkinTs.getTime());
	long diff = TimeUnit.MILLISECONDS.toMinutes(checkinTs.getTime() - checkoutTs.getTime());
	// LOGGER.info("" + diff);
	if ( diff <= 30 ) return  SVC_FEE + BASE_RATE;
	
	return SVC_FEE + BASE_RATE + (( diff - BASE_MINUTES - 1 ) / INCR_MINUTES + 1) * INCR_RATE;
    }
    
}
