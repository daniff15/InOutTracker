package in.out.tracker.clients;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import in.out.tracker.model.Mall;
import io.github.majusko.pulsar.annotation.PulsarConsumer;
import org.apache.pulsar.shade.org.apache.avro.data.Json;
import org.springframework.stereotype.Service;

@Service
public class Consumer{
    @PulsarConsumer(topic="ns1/people-count", clazz = Mall.class)
    void consume(Mall msg) {
        // TODO process your message
        System.out.println(msg.toString());
    }
}
