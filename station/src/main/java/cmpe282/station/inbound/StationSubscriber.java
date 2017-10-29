package cmpe282.station.inbound;

import static cmpe282.pubsub.Subscriptions.mySub;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.gcp.pubsub.inbound.PubSubInboundChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

import cmpe282.pubsub.inbound.CmpeSubscriber;
import cmpe282.station.msghandler.MyTopicMsgHandler;

@Component
public class StationSubscriber extends CmpeSubscriber {

    @Bean
    public MessageChannel stationSubscriberChannel() {
	return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "stationSubscriberChannel")
    public MessageHandler stationMsgReceiver() {
	return new MyTopicMsgHandler();
    }

    @Bean
    public PubSubInboundChannelAdapter stationChannelAdapter(
	    @Qualifier("stationSubscriberChannel") MessageChannel inputChannel,
	    PubSubOperations pubSubTemplate) {
	return buildChannelAdapter(inputChannel, pubSubTemplate, mySub.name());
    }

}
