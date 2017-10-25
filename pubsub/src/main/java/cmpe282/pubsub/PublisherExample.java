package cmpe282.pubsub;

import java.util.logging.Logger;

import com.google.api.core.ApiFuture;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

public class PublisherExample {

	private static final int MESSAGE_COUNT = 5;
	private static final String PROJECT_ID = ServiceOptions.getDefaultProjectId();
	private static Logger log = Logger.getLogger(PublisherExample.class.getName());
	
	public static ApiFuture<String> publishMsg(Publisher publisher, String message){
		ByteString data = ByteString.copyFromUtf8(message);
		PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
		return publisher.publish(pubsubMessage);
	}
	
	
	public static void main(String... args){
		String topicId = args[0];
		TopicName topicName = TopicName.create(PROJECT_ID, topicId);
		log.info(PROJECT_ID);
		log.info(topicName.toString());
		try {
			Publisher publisher = Publisher.defaultBuilder(topicName).build();
			
			for (int i = 0; i < MESSAGE_COUNT; i++) {
				String message = "message - " + i;
				log.info(message);
				ApiFuture<String> msgId = publishMsg(publisher, message);
				log.info(msgId.get());
			}
			
			if ( publisher != null )
				publisher.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
