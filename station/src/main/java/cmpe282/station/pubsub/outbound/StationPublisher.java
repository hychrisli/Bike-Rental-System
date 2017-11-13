package cmpe282.station.pubsub.outbound;

import static cmpe282.pubsub.Topics.myTopic2;

import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.gcp.pubsub.outbound.PubSubMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class StationPublisher {
    
    @Bean
    public MessageChannel stationOutChannel() {
	return new DirectChannel();
    }
    
    @Bean
    @ServiceActivator(inputChannel = "stationOutChannel")
    public MessageHandler stationMsgSender(PubSubOperations pubsubTemplate) {
      return new PubSubMessageHandler(pubsubTemplate, myTopic2.name());
    }
    
    @MessagingGateway(defaultRequestChannel = "stationOutChannel")
    public interface StationOutGateway {
      void sendToPubsub(String text);
    }
    
}
