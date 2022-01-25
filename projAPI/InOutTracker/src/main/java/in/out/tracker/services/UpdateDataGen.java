package in.out.tracker.services.produce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateDataGen {

    String serviceURL = "localhost";
    PulsarClient client = PulsarClient.builder()
            .serviceUrl(serviceURL)
            .build();
    private Producer<String> producer = client.newProducer(Schema.STRING)
            .topic("updates")
            .create();

    public UpdateDataGen() throws PulsarClientException {
    }

    public void informDataGen(){
        try {
            String json = "update";
            producer.send(json);
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

}
