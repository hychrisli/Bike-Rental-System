package cmpe282.station;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PubSubApp {
    
    public static void main(String[] args) throws IOException{
	SpringApplication.run(PubSubApp.class, args);
    }
    
}
