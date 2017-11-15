package cmpe282.station.pubsub.msghandler;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.web.client.RestTemplate;

import cmpe282.station.cassandra.entity.Employee;

public class EmployeeMsgHandler implements MessageHandler {

    @Override
    public void handleMessage(Message<?> arg0) throws MessagingException {
	// TODO Auto-generated method stub
	
    }
    
    @Bean
    private Employee msgToPojo(RestTemplate restTemplate){
	restTemplate.getForObject(url, Employee.class);
    }

}
