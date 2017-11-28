package cmpe282.station.pubsub.msghandler;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.support.GcpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.web.client.RestTemplate;

import com.google.cloud.pubsub.v1.AckReplyConsumer;

import cmpe282.message.mq.ConfirmMsg;
import cmpe282.message.mq.ReservMsg;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ReservMsgHandler implements MessageHandler {

    private static Logger LOGGER = Logger.getLogger(ReservMsgHandler.class.getName());
    private static ObjectMapper objMapper = new ObjectMapper();
    
    @Autowired
    RestTemplate restTemplate;
    
    @Override
    public void handleMessage(Message<?> msg) throws MessagingException {
	LOGGER.info(msg.getPayload().toString());
	AckReplyConsumer consumer = (AckReplyConsumer) msg.getHeaders().get(GcpHeaders.ACKNOWLEDGEMENT);
	consumer.ack();
	try {
	    ReservMsg reservMsg = objMapper.readValue(msg.getPayload().toString(), ReservMsg.class);
	    LOGGER.info("Receive a Message: " + objMapper.writeValueAsString(reservMsg));
	    ConfirmMsg confirmMsg = restTemplate.postForObject("http://localhost:8080/brs/api/station/reserve", reservMsg, ConfirmMsg.class);
	    LOGGER.info("Reservation Response: " + objMapper.writeValueAsString(confirmMsg));
	} catch (IOException e) {
	    LOGGER.log(Level.WARNING, e.getMessage(), e);
	}
    }
}
