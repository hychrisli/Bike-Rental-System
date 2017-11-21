package cmpe282.station.service;

import com.google.api.core.ApiFuture;

public interface PublisherService {

    ApiFuture<String> publishMessage(String topic, String message) throws Exception;
}
