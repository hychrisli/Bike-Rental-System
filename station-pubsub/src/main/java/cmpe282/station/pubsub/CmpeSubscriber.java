package cmpe282.station.pubsub;


import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.integration.gcp.pubsub.AckMode;
import org.springframework.integration.gcp.pubsub.inbound.PubSubInboundChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;


public abstract class CmpeSubscriber {

    public PubSubInboundChannelAdapter buildChannelAdapter(
	    MessageChannel inputChannel, PubSubOperations pubSubTemplate,
	    String subscription) {
	PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, subscription);
	adapter.setOutputChannel(inputChannel);
	adapter.setAckMode(AckMode.MANUAL);

	return adapter;
    }
    
}
