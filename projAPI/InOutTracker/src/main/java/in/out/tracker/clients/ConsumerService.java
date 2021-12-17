package in.out.tracker.clients;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import in.out.tracker.model.Mall;
import io.github.majusko.pulsar.annotation.PulsarConsumer;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;


//@Service
public class ConsumerService {
    public AtomicBoolean stringReceived = new AtomicBoolean(false);

    //@PulsarConsumer(topic="ns1/people-count", clazz = String.class, consumerName = "consumer", subscriptionName = "DB Updates")
    void consumeString(String msg){
        /** TODO process your message
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        System.out.println(gson.toJson(jp.parse(String.valueOf(msg))));
         **/
        System.out.println(msg);
        stringReceived.set(true);
    }
}
