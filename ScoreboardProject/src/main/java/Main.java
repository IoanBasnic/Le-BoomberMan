import domain.GameStartData;
import kafka.KafkaConsumerGameInitializer;
import kafka.KafkaConsumerInputData;
import kafka.KafkaProducerOutputData;
import processing.DataProcessor;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        KafkaConsumerGameInitializer kafkaConsumerGameInitializer = new KafkaConsumerGameInitializer();
        GameStartData gameStartData = kafkaConsumerGameInitializer.runConsumer();

        KafkaProducerOutputData kafkaProducer = new KafkaProducerOutputData();
        DataProcessor dataProcessor = new DataProcessor(gameStartData, kafkaProducer);

        KafkaConsumerInputData kafkaConsumerInputData = new KafkaConsumerInputData(dataProcessor);
        kafkaConsumerInputData.runConsumer();
    }
}

