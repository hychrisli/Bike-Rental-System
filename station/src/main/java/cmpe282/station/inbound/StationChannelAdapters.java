package cmpe282.station.inbound;

import static cmpe282.pubsub.Subscriptions.mySub;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.gcp.pubsub.inbound.PubSubInboundChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import cmpe282.pubsub.inbound.ChannelAdapter;

@Component
public class StationChannelAdapters extends ChannelAdapter{

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
	    @Qualifier("pubsubInputChannel") MessageChannel inputChannel, PubSubOperations pubSubTemplate) {
	return buildChannelAdapter(inputChannel, pubSubTemplate, mySub.name());
    }
}
