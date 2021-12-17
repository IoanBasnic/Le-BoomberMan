package kafka;

        import com.fasterxml.jackson.databind.ObjectMapper;
        import domain.GameOver;
        import domain.TopicNames;
        import org.apache.kafka.clients.consumer.Consumer;
        import org.apache.kafka.clients.consumer.ConsumerRecords;

        import java.io.IOException;

public class KafkaConsumerGameOver extends BaseKafkaConsumer{

    public KafkaConsumerGameOver() {
        super(TopicNames.gameOver);
    }

    public void runConsumer() {
        final Consumer<Long, String> consumer = createConsumer();
        ObjectMapper objectMapper = new ObjectMapper();

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    consumer.poll(1000);

            consumerRecords.forEach(record -> {
                GameOver data;
                try {
                    data = objectMapper.readValue(record.value(), GameOver.class);

                    System.out.println(data.gameOver);

                    if(data.gameOver) {

                        consumer.close();
                        System.exit(0);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }
    }
}
