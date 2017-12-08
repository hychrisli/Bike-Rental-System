package cmpe282.station.service;

public interface PublisherService {

    String publishMessage(String topic, Object message) throws Exception;
}
