package processing;


import domain.GameStartData;
import domain.BombExplosionData;
import domain.ProcessingResultData;
import domain.KafkaPlayer;
import kafka.KafkaProducerProcessedResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataProcessor {
    private GameStartData gameStartData;
    private HashMap<Integer, KafkaPlayer> players; //map from player id to Player
    private KafkaProducerProcessedResult kafkaProducer;

    public DataProcessor(GameStartData data, KafkaProducerProcessedResult kafkaProducer){
        gameStartData = data;
        this.kafkaProducer = kafkaProducer;
        players = new HashMap<Integer, KafkaPlayer>();

        for(int i = 0; i < data.numberOfPlayers; i++){
            KafkaPlayer player = new KafkaPlayer();
            player.playerId = i;
            player.lives = data.livesPerPlayer;
            player.score = 0;

            if(i < data.playerNames.size())
                player.name = data.playerNames.get(i);
            else player.name = "player with id " + i;

            players.put(i, player);
        }
    }

    public void handleBombExplosionData(BombExplosionData data) throws Exception {
        KafkaPlayer exploderPlayer = players.get(data.playerId);

        //"destroy" boxes
        if(data.boxesDestroyed <= gameStartData.numberOfBoxes){
            gameStartData.numberOfBoxes -= data.boxesDestroyed;

            exploderPlayer.score += (data.boxesDestroyed * gameStartData.pointsForBox);
        }

        //"kill" exploded players
        for(int playerId: data.playerLivesTaken){
            //account for the case where the player bombed themselves
            if(playerId == exploderPlayer.playerId && exploderPlayer.lives > 0){
                exploderPlayer.lives -= 1;
            }
            else {
                KafkaPlayer explodedPlayer = players.get(playerId);
                if(explodedPlayer.lives > 0){
                    explodedPlayer.lives--;

                    exploderPlayer.score += gameStartData.pointsForLife;

                    players.replace(playerId, explodedPlayer);
                }
            }
        }
        players.replace(exploderPlayer.playerId, exploderPlayer);

        ProcessingResultData dataToSend = new ProcessingResultData();

        dataToSend.gameStatus = checkIfGameOver();
        dataToSend.players = new ArrayList<>(players.values());

        if(dataToSend.gameStatus > 0) {
            dataToSend.winner = new ArrayList<KafkaPlayer>();
            dataToSend.winner.add(players.get(dataToSend.gameStatus - 1));
        }

        kafkaProducer.send(dataToSend);

        //when sending winning data
        if(dataToSend.gameStatus != 0){
            kafkaProducer.close();
            return;
        }
    }

    private int checkIfGameOver(){
        ArrayList<Integer> countAlivePlayers = new ArrayList<Integer>();

        List<KafkaPlayer> playersSeq = new ArrayList<>(players.values());

        for(KafkaPlayer player: playersSeq){
            if(player.lives > 0) countAlivePlayers.add(player.playerId);
        }

        if(countAlivePlayers.size() == 0) return -1;
        else if(countAlivePlayers.size() == 1) return countAlivePlayers.get(0) + 1;
        else return 0;
    }
}
