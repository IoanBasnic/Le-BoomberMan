package kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.BombExplosionData;
import domain.TopicNames;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import processing.DataProcessor;

public class KafkaConsumerBombExplosion extends BaseKafkaConsumer {
    DataProcessor dataProcessor;

    public KafkaConsumerBombExplosion(DataProcessor dataProcessor) {
        super(TopicNames.bombExplosionData);
        this.dataProcessor = dataProcessor;
    }

    public void runConsumer() {
        final Consumer<Long, String> consumer = createConsumer();
        ObjectMapper objectMapper = new ObjectMapper();

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    consumer.poll(1000);

            consumerRecords.forEach(record -> {
                try {
                    BombExplosionData data = objectMapper.readValue(record.value(), BombExplosionData.class);
                    dataProcessor.handleBombExplosionData(data);

                    System.out.print(data.playerId + " " + data.boxesDestroyed + " " + data.playerLivesTaken);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            consumer.commitAsync();
        }
    }

}
