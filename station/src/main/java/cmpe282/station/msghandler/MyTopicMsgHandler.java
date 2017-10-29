package cmpe282.station.msghandler;

import java.util.logging.Logger;

import org.springframework.cloud.gcp.pubsub.support.GcpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import com.google.cloud.pubsub.v1.AckReplyConsumer;

public class MyTopicMsgHandler implements MessageHandler {

    private static Logger LOGGER = Logger.getLogger(MyTopicMsgHandler.class.getName());
    
    @Override
    public void handleMessage(Message<?> msg) throws MessagingException {
	LOGGER.info("Message arrived! Payload: " + msg.getPayload());
        AckReplyConsumer consumer =
            (AckReplyConsumer) msg.getHeaders().get(GcpHeaders.ACKNOWLEDGEMENT);
        consumer.ack();
    }

}
