package kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class BaseKafkaConsumer {

    private String topicName;

    public BaseKafkaConsumer(String topicName){
        this.topicName = topicName;
    }

    public interface IKafkaConstants {
        public static String BOOTSTRAP_SERVERS =
                "localhost:9092,localhost:9093,localhost:9094";

        public static Integer MESSAGE_COUNT=1000;

        public static String CLIENT_ID="client1";

        public static String GROUP_ID_CONFIG="consumerGroup1";

        public static Integer MAX_NO_MESSAGE_FOUND_COUNT=100;

        public static String OFFSET_RESET_LATEST="latest";

        public static String OFFSET_RESET_EARLIER="earliest";

        public static Integer MAX_POLL_RECORDS=1;
    }

    public Consumer<Long, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, IKafkaConstants.GROUP_ID_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());


        Consumer<Long, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topicName));
        return consumer;
    }
}
