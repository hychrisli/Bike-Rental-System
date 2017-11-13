package cmpe282.station.restapi.controller;

import static cmpe282.station.restapi.constant.JsonConstant.KEY_ERROR;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cmpe282.station.restapi.response.JsonResponse;
import cmpe282.station.restapi.response.JsonResponseHandler;

public abstract class AbstractController extends JsonResponseHandler {

    protected <T> ResponseEntity<JsonResponse> success(String key, T data) {
	return genericResponse(new JsonResponse(key, data), HttpStatus.OK);
    }

    protected ResponseEntity<JsonResponse> notFound() {
	return genericResponse(new JsonResponse(KEY_ERROR, HttpStatus.NOT_FOUND.name()), HttpStatus.NOT_FOUND);
    }
}