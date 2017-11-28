package cmpe282.station.service.impl;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

import cmpe282.station.service.PublisherService;

@Service
public class PublisherServiceImpl implements PublisherService {

    private static Logger LOGGER = Logger.getLogger(PublisherServiceImpl.class.getName());
    private static final String PROJECT_ID = ServiceOptions.getDefaultProjectId();
    private static final ObjectMapper mapper = new ObjectMapper(); 
    
    @Override
    public String publishMessage(String topic, Object message) throws Exception {
	
	LOGGER.info(PROJECT_ID);
	TopicName topicName = TopicName.newBuilder()
		.setProject(PROJECT_ID)
		.setTopic(topic)
		.build();
	Publisher publisher = Publisher.newBuilder(topicName).build();
	return publishMessage(publisher, mapper.writeValueAsString(message)).get();
    }

    private static ApiFuture<String> publishMessage(Publisher publisher, String message) throws Exception {
	// convert message to bytes
	ByteString data = ByteString.copyFromUtf8(message);
	PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
	return publisher.publish(pubsubMessage);
    }
    
}
