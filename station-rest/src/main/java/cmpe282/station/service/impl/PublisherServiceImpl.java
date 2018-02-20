package cmpe282.station.service.impl;

import static cmpe282.station.error.ErrorCode.ERR_FAIL_TO_PUBLISH;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

import cmpe282.station.error.AppException;
import cmpe282.station.service.PublisherService;

@Service
public class PublisherServiceImpl implements PublisherService {

    private static Logger LOGGER = Logger.getLogger(PublisherServiceImpl.class.getName());
    private static final String PROJECT_ID = ServiceOptions.getDefaultProjectId();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String publishMessage(String topic, Object message) throws AppException {

	LOGGER.info(PROJECT_ID);
	TopicName topicName = TopicName.newBuilder().setProject(PROJECT_ID).setTopic(topic).build();
	String msgIdStr = null;
	try {
	    Publisher publisher = Publisher.newBuilder(topicName).build();   
	    msgIdStr = publishMessage(publisher, mapper.writeValueAsString(message)).get();
	} catch (IOException | InterruptedException | ExecutionException e) {
	    throw new AppException(ERR_FAIL_TO_PUBLISH, e);
	}
	return msgIdStr;
    }

    private static ApiFuture<String> publishMessage(Publisher publisher, String message) {
	// convert message to bytes
	ByteString data = ByteString.copyFromUtf8(message);
	PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
	return publisher.publish(pubsubMessage);
    }

}
