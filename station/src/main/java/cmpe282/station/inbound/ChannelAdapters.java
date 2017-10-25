package cmpe282.station.inbound;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.gcp.pubsub.AckMode;
import org.springframework.integration.gcp.pubsub.inbound.PubSubInboundChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public class ChannelAdapters {
    
    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
	    @Qualifier("pubsubInputChannel") MessageChannel inputChannel,
        PubSubOperations pubSubTemplate) {
      PubSubInboundChannelAdapter adapter =
          new PubSubInboundChannelAdapter(pubSubTemplate, "mySub");
      adapter.setOutputChannel(inputChannel);
      adapter.setAckMode(AckMode.MANUAL);

      return adapter;
    }
}
