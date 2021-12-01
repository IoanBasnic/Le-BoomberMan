import domain.GameStartData;
import kafka.KafkaConsumerGameInitializer;
import kafka.KafkaConsumerBombExplosion;
import kafka.KafkaProducerProcessedResult;
import processing.DataProcessor;

public class Main {

    public static void main(String[] args) {
        KafkaConsumerGameInitializer kafkaConsumerGameInitializer = new KafkaConsumerGameInitializer();
        GameStartData gameStartData = kafkaConsumerGameInitializer.runConsumer();

        KafkaProducerProcessedResult kafkaProducer = new KafkaProducerProcessedResult();
        DataProcessor dataProcessor = new DataProcessor(gameStartData, kafkaProducer);

        KafkaConsumerBombExplosion kafkaConsumerBombExplosion = new KafkaConsumerBombExplosion(dataProcessor);
        kafkaConsumerBombExplosion.runConsumer();
    }
}

