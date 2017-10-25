package cmpe282.station.inbound;

import java.util.logging.Logger;

import org.springframework.cloud.gcp.pubsub.support.GcpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

import com.google.cloud.pubsub.v1.AckReplyConsumer;

@Component
public class MessageHandlers {
    
    private static Logger LOGGER = Logger.getLogger(MessageHandlers.class.getName());
    
    @Bean
    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public MessageHandler messageReceiver() {
      return message -> {
        LOGGER.info("Message arrived! Payload: " + message.getPayload());
        AckReplyConsumer consumer =
            (AckReplyConsumer) message.getHeaders().get(GcpHeaders.ACKNOWLEDGEMENT);
        consumer.ack();
      };
    }
}
