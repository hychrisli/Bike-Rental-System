package cmpe282.station.pubsub.inbound;

import static cmpe282.message.Subscriptions.mySub;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.gcp.pubsub.inbound.PubSubInboundChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

import cmpe282.station.pubsub.CmpeSubscriber;
import cmpe282.station.pubsub.msghandler.Station2MsgHandler;
import cmpe282.station.pubsub.msghandler.StationMsgHandler;

@Component
public class StationSubscriber extends CmpeSubscriber {

    @Bean
    public MessageChannel stationInChannel() {
	return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "stationInChannel")
    public MessageHandler stationMsgReceiver() {
	return new Station2MsgHandler();
    }

    @Bean
    public PubSubInboundChannelAdapter stationChannelAdapter(
	    @Qualifier("stationInChannel") MessageChannel inputChannel,
	    PubSubOperations pubSubTemplate) {
	return buildChannelAdapter(inputChannel, pubSubTemplate, mySub.name());
    }

}
