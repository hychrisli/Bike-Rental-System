package cmpe282.station.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class JsonResponseHandler {
	
	protected ResponseEntity<JsonResponse> genericResponse (JsonResponse jsonResponse, HttpStatus httpStatus){
		return new ResponseEntity<JsonResponse>(jsonResponse, httpStatus);
	}
	
	protected ResponseEntity<JsonResponse> responseWithCustHeaders(JsonResponse jsonResponse, HttpHeaders headers, HttpStatus httpStatus){
		return new ResponseEntity<JsonResponse>(jsonResponse, headers, httpStatus);
	}
}
