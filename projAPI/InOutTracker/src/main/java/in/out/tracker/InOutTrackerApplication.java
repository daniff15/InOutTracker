package in.out.tracker;

import in.out.tracker.services.ShoppingService;
import in.out.tracker.services.StoreService;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication
public class InOutTrackerApplication {

	public static void main(String[] args) throws PulsarClientException {
		StoreService storeService = new StoreService();
		ShoppingService shoppingService = new ShoppingService();

		Date date = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -365);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		AtomicReference<String> day = new AtomicReference<>(formatter.format(cal.getTime()));

		ConfigurableApplicationContext ctx =
				SpringApplication.run(InOutTrackerApplication.class, args);
		String serviceURL = ctx.getEnvironment().getProperty("pulsar.service-url");


		PulsarClient client = PulsarClient.builder()
				.serviceUrl(serviceURL)
				.build();

		MessageListener messageListener = (consumer, msg) -> {
			try {
				String message = new String(msg.getData());
				JSONObject json = new JSONObject(message);
				JSONObject shoppings = json.getJSONObject("shoppings");
				for(Iterator it = shoppings.keys(); it.hasNext(); ) {
					String element = (String) it.next();
					String people = shoppings.getString(element);
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

				JSONObject stores = json.getJSONObject("stores");
				for(Iterator it = stores.keys(); it.hasNext(); ) {
					String element = (String) it.next();
					String people = stores.getString(element);
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

				JSONObject waiting_stores = json.getJSONObject("waiting_stores");
				for(Iterator it = waiting_stores.keys(); it.hasNext(); ) {
					String element = (String) it.next();
					String people = waiting_stores.getString(element);
					URL url = new URL("http://127.0.0.1:8000/api/v1/store/update/" + element + "/waiting/" + people);
					HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
					httpCon.setDoOutput(true);
					httpCon.setRequestMethod("PUT");
					OutputStreamWriter out = new OutputStreamWriter(
							httpCon.getOutputStream());
					out.write("Resource content");
					out.close();
					httpCon.getInputStream();
				}
				JSONObject daily_info = json.getJSONObject("daily_info");
				if (daily_info.length() > 0){
					cal.add(Calendar.DATE, 1);
					day.set(formatter.format(cal.getTime()));
				}
				for(Iterator it = daily_info.keys(); it.hasNext(); ) {
					String hour = (String) it.next();
					JSONObject hours = daily_info.getJSONObject(hour);
					for(Iterator el = hours.keys(); el.hasNext(); ) {
						String store = (String) el.next();
						int total_people = hours.getInt(store);
						URL url = new URL("http://127.0.0.1:8000/api/v1/add/daily/" + store + "/" + day + "/"+ hour + "/" + total_people);
						HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
						httpCon.setDoOutput(true);
						httpCon.setRequestMethod("POST");
						OutputStreamWriter out = new OutputStreamWriter(
						httpCon.getOutputStream());
						out.write("Resource content");
						out.close();
						httpCon.getInputStream();
					}
				}
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
