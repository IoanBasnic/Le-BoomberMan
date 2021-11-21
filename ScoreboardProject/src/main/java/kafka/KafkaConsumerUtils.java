package kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.InputData;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerUtils {
    public interface IKafkaConstants {
        public static String BOOTSTRAP_SERVERS =
                "localhost:9092,localhost:9093,localhost:9094";

        public static Integer MESSAGE_COUNT=1000;

        public static String CLIENT_ID="client1";

        public static String TOPIC_NAME="my-topic";

        public static String GROUP_ID_CONFIG="consumerGroup1";

        public static Integer MAX_NO_MESSAGE_FOUND_COUNT=100;

        public static String OFFSET_RESET_LATEST="latest";

        public static String OFFSET_RESET_EARLIER="earliest";

        public static Integer MAX_POLL_RECORDS=1;
    }

    private static Consumer<Long, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaExampleConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());


        Consumer<Long, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(IKafkaConstants.TOPIC_NAME));
        return consumer;
    }


    public void runConsumer() throws InterruptedException {
        final Consumer<Long, String> consumer = createConsumer();

        final int giveUp = 100;   int noRecordsCount = 0;
        ObjectMapper objectMapper = new ObjectMapper();

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    consumer.poll(1000);

            if (consumerRecords.count()==0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }

            consumerRecords.forEach(record -> {
                try {
                    InputData data = objectMapper.readValue(record.value(), InputData.class);

                    System.out.print(data.playerId + " " + data.boxesDestroyed + " " + data.playerLivesTaken);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            });

            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("DONE");
    }

}
