package in.out.tracker;

import in.out.tracker.clients.Consumer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InOutTrackerApplication {

	public static void main(String[] args) throws PulsarClientException { SpringApplication.run(InOutTrackerApplication.class, args); }

}
