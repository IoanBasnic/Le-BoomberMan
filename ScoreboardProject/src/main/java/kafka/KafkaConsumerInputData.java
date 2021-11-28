package kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.InputData;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import processing.DataProcessor;

import java.io.IOException;

public class KafkaConsumerInputData extends BaseKafkaConsumer {
    DataProcessor dataProcessor;

    public KafkaConsumerInputData(DataProcessor dataProcessor) {
        super("bomb-explosion-data-topic");
        this.dataProcessor = dataProcessor;
    }

    public void runConsumer() {
        final Consumer<Long, String> consumer = createConsumer();

        final int giveUp = 100;
        int noRecordsCount = 0;
        ObjectMapper objectMapper = new ObjectMapper();

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    consumer.poll(1000);

//            if (consumerRecords.count()==0) {
//                noRecordsCount++;
//                if (noRecordsCount > giveUp) break;
//                else continue;
//            }

            consumerRecords.forEach(record -> {
                try {
                    InputData data = objectMapper.readValue(record.value(), InputData.class);
                    dataProcessor.handleInputData(data);

                    System.out.print(data.playerId + " " + data.boxesDestroyed + " " + data.playerLivesTaken);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            consumer.commitAsync();
        }
//        consumer.close();
//        System.out.println("DONE");
    }

}
