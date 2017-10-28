package cmpe282.station.inbound;

import java.util.logging.Logger;

import org.springframework.cloud.gcp.pubsub.support.GcpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import com.google.cloud.pubsub.v1.AckReplyConsumer;

import cmpe282.station.msghandler.MyTopicMsgHandler;

@Component
public class MessageHandlers {
    
    private static Logger LOGGER = Logger.getLogger(MessageHandlers.class.getName());
    
    @Bean
    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public MessageHandler messageReceiver() {
	return new MyTopicMsgHandler();
    }
}
