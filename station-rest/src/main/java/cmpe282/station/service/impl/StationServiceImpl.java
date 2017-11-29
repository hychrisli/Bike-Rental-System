package cmpe282.station.service.impl;

import static cmpe282.message.Topics.TOPIC_COMPLETION;
import static cmpe282.message.Topics.TOPIC_CONFIRMATION;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cmpe282.message.direct.CheckinConfirmMsg;
import cmpe282.message.direct.CheckinReqMsg;
import cmpe282.message.direct.CheckoutConfirmMsg;
import cmpe282.message.direct.CheckoutReqMsg;
import cmpe282.message.direct.StationIdsMsg;
import cmpe282.message.mq.ComplMsg;
import cmpe282.message.mq.ConfirmMsg;
import cmpe282.message.mq.ReservMsg;
import cmpe282.station.entity.InBike;
import cmpe282.station.entity.OutBike;
import cmpe282.station.entity.RsvdBike;
import cmpe282.station.entity.Station;
import cmpe282.station.mapper.CheckoutMsgMapper;
import cmpe282.station.mapper.ComplMsgMapper;
import cmpe282.station.mapper.ConfirmMsgMapper;
import cmpe282.station.mapper.MapIdMapper;
import cmpe282.station.repository.StationRepository;
import cmpe282.station.service.BikeService;
import cmpe282.station.service.PublisherService;
import cmpe282.station.service.StationService;

@Service
@Transactional
public class StationServiceImpl implements StationService {

    private static Logger LOGGER = Logger.getLogger(StationServiceImpl.class.getName());

    @Autowired
    private StationRepository stationRepo;

    @Autowired
    private BikeService bikeSvc;

    @Autowired
    PublisherService pubSvc;

    @Override
    public Station getStation(String stationId) {
	return stationRepo.findOne(MapIdMapper.toMapId("stationId", stationId));
    }

    @Override
    public boolean updateAvailBikes(String stationId, int delta) {
	Station station = getStation(stationId);
	int availBikes = station.getAvailBikes() + delta;

	if (availBikes < 0 || availBikes > station.getTotalDocks())
	    return false;

	station.setAvailBikes(availBikes);
	stationRepo.save(station);
	return true;
    }

    @Override
    public boolean increaseAvailBikesByOne(String stationId) {
	return updateAvailBikes(stationId, 1);
    }

    @Override
    public boolean decreaseAvailBikesByOne(String stationId) {
	return updateAvailBikes(stationId, -1);
    }

    @Override
    public ConfirmMsg reserveOneBike(ReservMsg reservMsg) {

	ConfirmMsg confirmMsg = ConfirmMsgMapper.toNotOkMsg(reservMsg);
	
	Station station = getStation(reservMsg.getStationId());

	if (station != null && station.getAvailBikes() > 0) {
	    RsvdBike rsvdBike = bikeSvc.rsvBike(reservMsg.getStationId(), reservMsg.getTxnId(),
		    reservMsg.getUserId());

	    if (rsvdBike != null && decreaseAvailBikesByOne(rsvdBike.getStationId()))
		confirmMsg = ConfirmMsgMapper.toOkMsg(rsvdBike);
	}

	try {
	    pubSvc.publishMessage(TOPIC_CONFIRMATION.name(), confirmMsg);
	} catch (Exception e) {
	    LOGGER.warning(e.getMessage());
	}

	return confirmMsg;
    }

    @Override
    public CheckoutConfirmMsg checkoutOneBike(CheckoutReqMsg checkoutReqMsg) {
	OutBike outBike = bikeSvc.checkoutBike(checkoutReqMsg.getUserId(), checkoutReqMsg.getStationId());
	if (outBike == null)
	    return CheckoutMsgMapper.toNotOkMsg();
	else
	    return CheckoutMsgMapper.toOkMsg(outBike);
    }

    @Override
    public CheckinConfirmMsg checkinOneBike(CheckinReqMsg checkinMsg) {
	Station station = getStation(checkinMsg.getStationId());
	if (station == null || station.getAvailBikes() >= station.getTotalDocks())
	    return ComplMsgMapper.toNotOkCheckinMsg();

	InBike inBike = bikeSvc.checkinBike(checkinMsg.getBikeId(), checkinMsg.getStationId());
	if (inBike != null) {
	    increaseAvailBikesByOne(inBike.getToStationId());
	    ComplMsg complMsg = ComplMsgMapper.toComplMsg(inBike);
	    String messageId;
	    try {
		messageId = pubSvc.publishMessage(TOPIC_COMPLETION.name(), complMsg);
		return ComplMsgMapper.toOkCheckinMsg(complMsg, messageId);
	    } catch (Exception e) {
		LOGGER.warning(e.getMessage());
	    }
	}

	return ComplMsgMapper.toNotOkCheckinMsg();
    }

    @Override
    public StationIdsMsg getStationIds() {
	Iterable<Station> stations = stationRepo.findAll();
	List<String> stationIds = new ArrayList<String>();
	stations.forEach(s -> stationIds.add(s.getStationId()));
	StationIdsMsg stationIdsMsg = new StationIdsMsg();
	stationIdsMsg.setStationIds(stationIds);
	
	return stationIdsMsg;
    }
}
