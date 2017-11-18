package cmpe282.station.controller;

import static cmpe282.message.Topics.TOPIC_RESERVATION;
import static cmpe282.station.config.UrlConstants.STATION;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.api.core.ApiFuture;

import cmpe282.message.ConfirmMsg;
import cmpe282.message.ReservMsg;
import cmpe282.station.entity.Station;
import cmpe282.station.service.PublisherService;
import cmpe282.station.service.StationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@Api(tags = {"Station"})
@SwaggerDefinition(tags = { @Tag(name="Sation Controller", description="Station Controller Endpoints")})
public class StationContoller extends AbstractController {

    private static Logger LOGGER = Logger.getLogger(StationContoller.class.getName());

    @Autowired
    StationService stationSvc;
    
    @Autowired
    PublisherService pubSvc;

    @ApiOperation(value = "Get Station Detail by ID")
    @GetMapping(STATION + "{station_id}")
    public ResponseEntity<Station> getStationDetail(@PathVariable int station_id) throws JsonProcessingException {
	Station station = stationSvc.getStation(station_id);
	if (station != null)
	    return success(station);

	return notFound(station);
    }

    @ApiOperation(value = "Reserve a bike")
    @PostMapping(STATION)
    public ResponseEntity<ConfirmMsg> reserveBike(@RequestBody ReservMsg reservMsg ) {
	return success(stationSvc.reserveOneBike(reservMsg));
    }

    @ApiOperation(value = "Publish a message")
    @PostMapping(STATION + "/publish")
    public ResponseEntity<String> publishMsg(@RequestBody String msg) throws Exception {
	ApiFuture<String> messageId = pubSvc.publishMessage(TOPIC_RESERVATION.name(), msg);
	return success(messageId.get());
    }



}
