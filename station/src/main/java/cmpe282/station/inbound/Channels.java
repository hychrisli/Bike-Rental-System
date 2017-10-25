package cmpe282.station.inbound;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public class Channels {
    
    @Bean
    public MessageChannel pubsubInputChannel() {
      return new DirectChannel();
    }
}
