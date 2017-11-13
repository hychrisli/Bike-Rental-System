package cmpe282.station.restapi.controller;

import static cmpe282.station.restapi.constant.JsonConstant.KEY_STATION_DETAIL;
import static cmpe282.station.restapi.constant.UrlConstant.STATION_DETAIL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cmpe282.station.entity.StationDetail;
import cmpe282.station.restapi.response.JsonResponse;
import cmpe282.station.service.StationDetailService;

@RestController
public class StationDetailController extends AbstractController {

    @Autowired
    StationDetailService stationDetailSvc;

    @GetMapping(STATION_DETAIL + "{station_id}")
    public ResponseEntity<JsonResponse> getStationDetail(@PathVariable int station_id) {
	StationDetail stationDetail = stationDetailSvc.findStationDetail(station_id);
	if (stationDetail != null)
	    return success(KEY_STATION_DETAIL, stationDetail);

	return notFound();
    }
}
