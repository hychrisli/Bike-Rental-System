package cmpe282.pubsub.example;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

import com.google.api.gax.rpc.AlreadyExistsException;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.SubscriptionName;
import com.google.pubsub.v1.TopicName;

public class SubscriberExample {

    private static final String PROJECT_ID = ServiceOptions.getDefaultProjectId();
    private static final BlockingQueue<PubsubMessage> messages = new LinkedBlockingDeque<>();
    private static Logger log = Logger.getLogger(SubscriberExample.class.getName());
    
    static class MessageReceiverExample implements MessageReceiver{

	@Override
	public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
	   messages.offer(message);
	   consumer.ack();
	}
    }
    
    public static void main(String... args) throws Exception{
	String topicId = args[0];
	String subscriptionId = args[1];
	TopicName topicName = TopicName.create(PROJECT_ID, topicId);
	SubscriptionName subscriptionName = SubscriptionName.create(PROJECT_ID, subscriptionId);
	log.info(PROJECT_ID);
	log.info(subscriptionName.toString());
	
	// If topic not exist, create topic
	try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
	    topicAdminClient.createTopic(topicName);
	}catch (AlreadyExistsException e) {
	    log.info("topic exists: " + topicName.toString());
	} catch (Exception e1) {
	    System.exit(1);
	} 
	
	// If subscription not exist, create subscription
	try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()){
	    PushConfig pushConfig = PushConfig.newBuilder().build();
	    int ackDeadlineSeconds = 0;
	    subscriptionAdminClient.createSubscription(subscriptionName, topicName, pushConfig, ackDeadlineSeconds);
	}catch (AlreadyExistsException e) {
	    log.info("subscription exists: " + topicName.toString());
	}catch (Exception e1) {
	    e1.printStackTrace();
	    System.exit(1);
	}

	Subscriber subscriber = null;
	try{
	    subscriber = Subscriber.defaultBuilder(subscriptionName, new MessageReceiverExample()).build();
	    subscriber.startAsync().awaitRunning();
	    while (true) {
		PubsubMessage message = messages.take();
		log.info("Message Id: " + message.getMessageId() + "\nData: " + message.getData().toStringUtf8());
	    }
	}catch (Exception e) {
	    e.printStackTrace();
	}finally{
	    if (subscriber != null) {
		subscriber.stopAsync();
	    }
	}
    }
    
}
