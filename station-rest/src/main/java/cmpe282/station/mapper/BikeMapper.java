package cmpe282.station.mapper;

import cmpe282.station.entity.OutBike;

import java.sql.Timestamp;

import cmpe282.station.entity.InBike;
import cmpe282.station.entity.RsvdBike;
import cmpe282.station.entity.StationBike;

public class BikeMapper {

    public static StationBike toStationBike (InBike inBike){
	
	StationBike stationBike = new StationBike();
	stationBike.setStationId(inBike.getToStationId());
	stationBike.setBikeId(inBike.getBikeId());
	
	return stationBike;
    }
    
    public static StationBike toStationBike (RsvdBike rsvdBike) {
	
	StationBike stationBike = new StationBike();
	stationBike.setStationId(rsvdBike.getStationId());
	stationBike.setBikeId(rsvdBike.getBikeId());
	
	return stationBike;
    }
    
    public static RsvdBike toRsvdBike (StationBike stationBike, String userId, String txnId){
	
	RsvdBike rsvdBike = new RsvdBike();
	rsvdBike.setUserId(userId);
	rsvdBike.setTxnId(txnId);
	rsvdBike.setStationId(stationBike.getStationId());
	rsvdBike.setBikeId(stationBike.getBikeId());
	
	return rsvdBike;
    }
    
    public static OutBike toOutBike (RsvdBike rsvdBike){
	
	OutBike outBike = new OutBike();
	outBike.setBikeId(rsvdBike.getBikeId());
	outBike.setFromStationId(rsvdBike.getStationId());
	outBike.setUserId(rsvdBike.getUserId());
	outBike.setTxnId(rsvdBike.getTxnId());
	outBike.setCheckoutTime(new Timestamp(System.currentTimeMillis()));
	
	return outBike;
    }
    
    public static InBike toInBike (OutBike outBike, String toStationId) {
	
	InBike inBike = new InBike();
	inBike.setTxnId(outBike.getTxnId());
	inBike.setBikeId(outBike.getBikeId());
	inBike.setUserId(outBike.getUserId());
	inBike.setTxnId(outBike.getTxnId());
	inBike.setFromStationId(outBike.getFromStationId());
	inBike.setCheckoutTime(outBike.getCheckoutTime());
	inBike.setToStationId(toStationId);
	inBike.setCheckinTime(new Timestamp(System.currentTimeMillis()));
	inBike.setGrandTotal(FareCalculator.calcFare(inBike.getCheckoutTime(), inBike.getCheckinTime()));
	
	return inBike;
    }
}
