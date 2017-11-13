package cmpe282.station.restapi.controller;

import static cmpe282.station.exception.ErrorCode.ERR_BAD_REQUEST;
import static cmpe282.station.exception.ErrorCode.ERR_INTERNAL_SERVER_ERROR;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cmpe282.station.exception.AppException;
import cmpe282.station.restapi.response.JsonResponse;
import cmpe282.station.restapi.response.JsonResponseHandler;

public class ControllerExceptionHandler extends JsonResponseHandler {
    private final static Logger LOGGER = getLogger(ControllerExceptionHandler.class);
 
    @ExceptionHandler(AppException.class)
    public ResponseEntity<JsonResponse> handleAppException(AppException appException) {
	LOGGER.error(appException.getErrorCode().name(), appException);
	return failure(appException.getErrorCode(), appException.getMessage());
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<JsonResponse> handleBadRequestException(HttpMessageNotReadableException e) {
	LOGGER.error(ERR_BAD_REQUEST.name(), e);
	return badRequest(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonResponse> handleException(Exception e) {
	LOGGER.error(ERR_INTERNAL_SERVER_ERROR.name(), e);
	return failure(ERR_INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
