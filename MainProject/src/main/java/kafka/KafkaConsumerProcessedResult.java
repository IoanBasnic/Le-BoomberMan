package kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.GameStartData;
import domain.ProcessingResultData;
import domain.TopicNames;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.io.IOException;

public class KafkaConsumerProcessedResult extends BaseKafkaConsumer{
    public KafkaConsumerProcessedResult() {
        super(TopicNames.result);
    }

    public GameStartData runConsumer() {
        final Consumer<Long, String> consumer = createConsumer();
        ObjectMapper objectMapper = new ObjectMapper();

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    consumer.poll(1000);

            consumerRecords.forEach(record -> {
                try {
                    ProcessingResultData data = objectMapper.readValue(record.value(), ProcessingResultData.class);

                    //TODO: do something with the result
                    System.out.print(data.gameStatus + " " + data.players + " " + data.winner);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            consumer.commitAsync();
        }
    }
}
