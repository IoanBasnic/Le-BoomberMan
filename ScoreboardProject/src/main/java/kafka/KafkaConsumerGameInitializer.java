package kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.GameStartData;
import domain.TopicNames;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.io.IOException;
import java.util.ArrayList;

public class KafkaConsumerGameInitializer extends BaseKafkaConsumer{

    public KafkaConsumerGameInitializer() {
        super(TopicNames.gameInitializer);
    }

    public GameStartData runConsumer() {
        final Consumer<Long, String> consumer = createConsumer();
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<GameStartData> gameStartData = new ArrayList<GameStartData>();

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    consumer.poll(1000);

            consumerRecords.forEach(record -> {
                GameStartData data;
                try {
                    data = objectMapper.readValue(record.value(), GameStartData.class);

                    System.out.println(data.numberOfPlayers + " " + data.livesPerPlayer + " " + data.numberOfBoxes + " " + data.pointsForBox + " " + data.pointsForLife);

                    gameStartData.add(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            consumer.commitAsync();

            if(!gameStartData.isEmpty()){
                consumer.close();
                System.out.println("Game initializer: retrieved initialization data.\nClosing consumer");
                return gameStartData.get(0);
            }
        }
    }
}
