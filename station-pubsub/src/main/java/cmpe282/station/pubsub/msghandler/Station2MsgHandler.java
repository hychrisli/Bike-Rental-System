package cmpe282.station.pubsub.msghandler;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.support.GcpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import com.google.cloud.pubsub.v1.AckReplyConsumer;

import cmpe282.message.Station;
import cmpe282.message.StationMsg;

import com.fasterxml.jackson.databind.ObjectMapper;


public class Station2MsgHandler implements MessageHandler {

    private static Logger LOGGER = Logger.getLogger(Station2MsgHandler.class.getName());
    
    @Autowired
    RestTemplate restTemplate;
    
    @Override
    public void handleMessage(Message<?> msg) throws MessagingException {
	ObjectMapper mapper = new ObjectMapper();
	LOGGER.info(msg.getPayload().toString());
	AckReplyConsumer consumer = (AckReplyConsumer) msg.getHeaders().get(GcpHeaders.ACKNOWLEDGEMENT);
	consumer.ack();
	try {
	    StationMsg stationMsg = mapper.readValue(msg.getPayload().toString(), StationMsg.class);
	    LOGGER.info(" " + stationMsg.getId());
	    String json = restTemplate.getForObject("http://localhost:8080/rest/station/" + stationMsg.getId(), String.class);
	    LOGGER.info(json);
	    Station station = mapper.readValue(json, Station.class);
	    LOGGER.info(station.getName());
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}

    }

}
