package kafka;

import domain.OutputData;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class KafkaProducerOutputData extends BaseKafkaProducer {
    public KafkaProducerOutputData() {
        super("result-topic");
    }

    public void send(OutputData data) throws Exception{
        long time = System.currentTimeMillis();

        try{
            ProducerRecord<Long, String> record = new ProducerRecord<>("result-topic", time, objectMapper.writeValueAsString(data));
            RecordMetadata metadata = producer.send(record).get();

            long elapsedTime = System.currentTimeMillis() - time;
            System.out.printf("sent record(key=%s value=%s) " +
                            "meta(partition=%d, offset=%d) time=%d\n",
                    record.key(), record.value(), metadata.partition(),
                    metadata.offset(), elapsedTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
