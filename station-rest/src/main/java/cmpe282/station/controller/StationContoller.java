package cmpe282.station.controller;

import static cmpe282.station.config.UrlConstants.STATION;

import java.util.logging.Logger;

import static cmpe282.station.config.JsonConstants.KEY_STATION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cmpe282.station.config.JsonResponse;
import cmpe282.station.entity.Station;
import cmpe282.station.service.StationService;

@RestController
public class StationContoller extends AbstractController {
    
    private static Logger LOGGER = Logger.getLogger(StationContoller.class.getName());
    
    @Autowired
    StationService stationSvc;

    @GetMapping(STATION + "{station_id}")
    public ResponseEntity<String> getStationDetail(@PathVariable int station_id) throws JsonProcessingException {
	ObjectMapper mapper = new ObjectMapper();
	LOGGER.info("I'm here");
	Station station = stationSvc.findStationDetail(station_id);
	if (station != null)
	    return new ResponseEntity<String> (mapper.writeValueAsString(station), HttpStatus.OK);

	return new ResponseEntity<String> ("Not Found", HttpStatus.NOT_FOUND);
    }
}
