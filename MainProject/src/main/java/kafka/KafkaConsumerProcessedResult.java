package kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.GameStartData;
import domain.KafkaPlayer;
import domain.ProcessingResultData;
import domain.TopicNames;
import frontend.UI.DrawObject.DrawScoreboard;
import frontend.UI.UiFrame;
import frontend.game_components.Player;
import map_tracker.GameMapInitializer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                    DrawScoreboard drawScoreboard = frame.getUiComponent().getScoreboard();

                    int playerCount = 0;
                    List<Player> playersInMainProject = gameMap.getPlayersList();

                    ArrayList<Integer> scoresAtBeginning = drawScoreboard.getPlayersScores();

                    for(KafkaPlayer playerFromKafka : data.players){
                        Player currentPlayerInMain = playersInMainProject.stream()
                                .peek(player -> System.out.println("will filter " + player))
                                .filter(x -> x.getId() == playerFromKafka.playerId)
                                .findFirst()
                                .get();

                        if(playerFromKafka.lives != currentPlayerInMain.getNoLifes()){
                            System.out.println("WARNING! Player with id: " + playerFromKafka.playerId + " has a different amount of lives in Scoreboard project " + currentPlayerInMain.getNoLifes() + " vs " + playerFromKafka.lives);
                        }

                        if(!scoresAtBeginning.get(playerCount).equals(playerFromKafka.score))
                            drawScoreboard.setPlayerScoreById(playerCount, playerFromKafka.score);

                        playerCount++;
                    }

                    System.out.print(data.gameStatus);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            consumer.commitAsync();
        }
    }
}
