package in.out.tracker.clients;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.schema.JSONSchema;


public class Consumer {
    PulsarClient pulsarClient = ClientUtils.initPulsarClient();
    public Consumer() throws PulsarClientException {
        Reader<byte[]> reader = pulsarClient.newReader()
                .topic("persistent://sample/standalone/ns1/helloworld")
                .startMessageId(MessageId.latest)
                .create();
        while (true) {
            Message message = reader.readNext();
            System.out.println(message);
        }
    }
}
