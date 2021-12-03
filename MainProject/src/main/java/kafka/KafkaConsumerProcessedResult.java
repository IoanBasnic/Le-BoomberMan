package kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.GameStartData;
import domain.KafkaPlayer;
import domain.ProcessingResultData;
import domain.TopicNames;
import frontend.UI.DrawObject.DrawHearts;
import frontend.UI.DrawObject.DrawScoreboard;
import frontend.UI.UiFrame;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.io.IOException;

public class KafkaConsumerProcessedResult extends BaseKafkaConsumer{
    UiFrame frame;

    public KafkaConsumerProcessedResult(UiFrame frame) {
        super(TopicNames.result);
        this.frame = frame;
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

                    //process data
                    DrawHearts drawHearts = frame.getUiComponent().getDrawHearts();
                    DrawScoreboard drawScoreboard = frame.getUiComponent().getScoreboard();

                    int playerCount = 0;
                    for(KafkaPlayer player : data.players){
                        drawHearts.setPlayerHeartsById(playerCount, player.lives);
                        drawScoreboard.setPlayerScoreById(playerCount, player.score);
                        playerCount++;
                    }

                    System.out.print(data.gameStatus + " " + data.players + " " + data.winner);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            consumer.commitAsync();
        }
    }
}
