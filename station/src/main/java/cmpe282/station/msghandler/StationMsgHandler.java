package cmpe282.station.msghandler;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.support.GcpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import com.google.cloud.pubsub.v1.AckReplyConsumer;

import cmpe282.station.outbound.StationPublisher.StationOutGateway;

public class StationMsgHandler implements MessageHandler {

    private static Logger LOGGER = Logger.getLogger(StationMsgHandler.class.getName());
    
    @Autowired
    private StationOutGateway msgGateway;
    
    @Override
    public void handleMessage(Message<?> msg) throws MessagingException {
	LOGGER.info("Message arrived! Payload: " + msg.getPayload());
	msgGateway.sendToPubsub(msg.getPayload().toString());
        AckReplyConsumer consumer =
            (AckReplyConsumer) msg.getHeaders().get(GcpHeaders.ACKNOWLEDGEMENT);
        consumer.ack();
    }

}
