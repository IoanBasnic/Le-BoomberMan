import kafka.KafkaConsumerUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        KafkaConsumerUtils kafkaConsumerUtils = new KafkaConsumerUtils();
        kafkaConsumerUtils.runConsumer();
    }

}

