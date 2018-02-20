package cmpe282.station.service;

import cmpe282.station.error.AppException;

public interface PublisherService {

    String publishMessage(String topic, Object message) throws AppException;
}
