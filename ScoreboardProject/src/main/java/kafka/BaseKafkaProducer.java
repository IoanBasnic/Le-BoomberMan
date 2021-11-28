package kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.OutputData;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class BaseKafkaProducer {
        private String topicName;
        Producer<Long, String> producer;
        ObjectMapper objectMapper = new ObjectMapper();

        public BaseKafkaProducer(String topicName){
            this.topicName = topicName;
            createProducer();
        }

        public interface IKafkaConstants {
            public static String BOOTSTRAP_SERVERS =
                    "localhost:9092,localhost:9093,localhost:9094";


            public static String CLIENT_ID="client1";
        }

    private void createProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                IKafkaConstants.BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, IKafkaConstants.CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        producer = new KafkaProducer<>(props);
    }

    public void close(){
        producer.flush();
        producer.close();
    }
}
