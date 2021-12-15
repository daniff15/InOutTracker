package in.out.tracker.clients;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class ClientUtils {
    public static PulsarClient initPulsarClient() throws PulsarClientException{
        return  PulsarClient.builder()
                .serviceUrl("127.0.0.1")
                .build();
    }
}
