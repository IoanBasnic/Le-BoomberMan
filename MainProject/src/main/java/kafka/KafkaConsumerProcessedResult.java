package kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.GameStartData;
import domain.KafkaPlayer;
import domain.ProcessingResultData;
import domain.TopicNames;
import frontend.UI.DrawObject.DrawHearts;
import frontend.UI.DrawObject.DrawScoreboard;
import frontend.UI.UiFrame;
import map_tracker.GameMapInitializer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.io.IOException;
import java.util.ArrayList;

public class KafkaConsumerProcessedResult extends BaseKafkaConsumer{
    UiFrame frame;
    GameMapInitializer gameMap;

    public KafkaConsumerProcessedResult(UiFrame frame, GameMapInitializer gameMap) {
        super(TopicNames.result);
        this.frame = frame;
        this.gameMap = gameMap;
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
                    ArrayList<Integer> heartsAtBeginning = drawHearts.getPlayersHearts();
                    ArrayList<Integer> scoresAtBeginning = drawScoreboard.getPlayersScores();

                    for(KafkaPlayer player : data.players){

                        if(!heartsAtBeginning.get(playerCount).equals(player.lives)) {
                            drawHearts.setPlayerHeartsById(playerCount, player.lives);

                            if(player.lives == 0){
                                gameMap.killPlayer(player.playerId);
                            }
                        }

                        if(!scoresAtBeginning.get(playerCount).equals(player.score))
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
