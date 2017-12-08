package cmpe282.station.controller;

import static cmpe282.station.config.JsonConstants.KEY_ERROR;
import static cmpe282.station.config.JsonConstants.KEY_MESSAGE;
import static cmpe282.station.config.JsonConstants.KEY_LOCATION;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import cmpe282.station.config.JsonResponse;
import cmpe282.station.config.JsonResponseHandler;


public abstract class AbstractController extends JsonResponseHandler {

	
	@Value("${server.address}")
	private String serverAddress;
	
	@Value("${server.port}")
	private String serverPort;
	
	protected <T> ResponseEntity<T> success(T data){
		return new ResponseEntity<T> (data, HttpStatus.OK);
	}
	
	protected <T> ResponseEntity<T> notFound(T data){
		return new ResponseEntity<T> (data, HttpStatus.NOT_FOUND);
	}
	
	protected ResponseEntity<JsonResponse> created(String loc){
		String url = "http://" + serverAddress + ":" + serverPort + loc;
		HttpHeaders headers = new HttpHeaders();
		headers.add(KEY_LOCATION, url);
		JsonResponse jsonResponse = new JsonResponse(KEY_MESSAGE, HttpStatus.CREATED.name());
		jsonResponse.addPair(KEY_LOCATION, url);
		return responseWithCustHeaders(jsonResponse, headers, HttpStatus.CREATED);
	}
	
	protected ResponseEntity<JsonResponse> conflict(){
		return genericResponse(new JsonResponse(KEY_ERROR, HttpStatus.CONFLICT.name()), HttpStatus.CONFLICT);
	}

}
