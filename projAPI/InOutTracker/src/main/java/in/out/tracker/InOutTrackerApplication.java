package in.out.tracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import in.out.tracker.exception.ResourceNotFoundException;
import in.out.tracker.services.FavStoresService;
import in.out.tracker.services.ShoppingService;
import in.out.tracker.services.StoreService;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.shade.org.apache.avro.data.Json;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;

@SpringBootApplication
public class InOutTrackerApplication {

	public static void main(String[] args) throws PulsarClientException {
		StoreService storeService = new StoreService();
		ShoppingService shoppingService = new ShoppingService();

		ConfigurableApplicationContext ctx =
				SpringApplication.run(InOutTrackerApplication.class, args);
		String serviceURL = ctx.getEnvironment().getProperty("pulsar.service-url");

		System.out.println(serviceURL);

		PulsarClient client = PulsarClient.builder()
				.serviceUrl(serviceURL)
				.build();

		MessageListener messageListener = (consumer, msg) -> {
			try {
				String message = new String(msg.getData());
				JSONObject json = new JSONObject(message);
				for (Iterator it = json.keys(); it.hasNext(); ) {
					String element = (String) it.next();
					String people = json.getString(element);
					if(!element.equals("0")) {
						URL url = new URL("http://127.0.0.1:8000/api/v1/store/update/" + element + "/count/" + people);
						HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
						httpCon.setDoOutput(true);
						httpCon.setRequestMethod("PUT");
						OutputStreamWriter out = new OutputStreamWriter(
								httpCon.getOutputStream());
						out.write("Resource content");
						out.close();
						httpCon.getInputStream();
					}
					else {
						URL url = new URL("http://127.0.0.1:8000/api/v1/shopping/update/" + element + "/count/" + people);
						HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
						httpCon.setDoOutput(true);
						httpCon.setRequestMethod("PUT");
						OutputStreamWriter out = new OutputStreamWriter(
								httpCon.getOutputStream());
						out.write("Resource content");
						out.close();
						httpCon.getInputStream();
					}
					/**
					 * if(!element.equals("0")){
						storeService.updateCount(Long.parseLong(element), Integer.parseInt(people));
					}

					else
						shoppingService.updateCount(Long.parseLong(element), Integer.parseInt(people));
					 **/
				}
				//System.out.println("Message received: " + message);
				consumer.acknowledge(msg);
			} catch (PulsarClientException e) {
				System.err.println(e);
				consumer.negativeAcknowledge(msg);
			} catch (JSONException e){
				System.err.println(e);
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		};

		Consumer consumer = client.newConsumer()
				.topic("persistent://public/default/ns1/people-count")
				.subscriptionName("update db")
				.messageListener(messageListener)
				.subscribe();

	}

}
