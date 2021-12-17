package in.out.tracker;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InOutTrackerApplication {

	public static void main(String[] args) throws PulsarClientException {
		PulsarClient client = PulsarClient.builder()
				.serviceUrl("pulsar://localhost:6650")
				.build();

		MessageListener messageListener = (consumer, msg) -> {
			try {
				System.out.println("Message received: " + new String(msg.getData()));
				consumer.acknowledge(msg);
			} catch (Exception e) {
				consumer.negativeAcknowledge(msg);
			}
		};

		Consumer consumer = client.newConsumer()
				.topic("persistent://public/default/ns1/people-count")
				.subscriptionName("update db")
				.messageListener(messageListener)
				.subscribe();

		SpringApplication.run(InOutTrackerApplication.class, args);
	}

}
