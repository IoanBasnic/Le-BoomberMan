import domain.GameStartData;
import frontend.UI.UiFrame;
import frontend.UI.UiGameOverFrame;
import kafka.KafkaConsumerProcessedResult;
import kafka.KafkaProducerBombExplosion;
import kafka.KafkaProducerGameInitializer;
import map_tracker.GameMapInitializer;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Main {
    private static final int TIME_STEP = 30;
    private static Timer clockTimer = null;
    private static final int width = 20;
    private static final int height = 20;

    private Main() {}

    public static void main(String[] args) {
         SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
                 System.out.println(Thread.currentThread().getName());
                 startGame();
             }
         });
    }

    private static void startGame() {
        KafkaProducerBombExplosion kafkaProducerBombExplosion = new KafkaProducerBombExplosion();
        GameMapInitializer floor = new GameMapInitializer(width, height, kafkaProducerBombExplosion);
        initialiseScoreboardProject(floor.getNumberOfBreakable());

        UiFrame frame = new UiFrame("Le Boomberman", floor);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        floor.addFloorListener(frame.getUiComponent());

        //kafka stuff
        KafkaConsumerProcessedResult processedResultProcessor = new KafkaConsumerProcessedResult(frame, floor);
        Thread thread = new Thread(){
            public void run(){
                processedResultProcessor.runConsumer();
            }
        };
        thread.start();

        Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    tick(frame, floor, kafkaProducerBombExplosion);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        clockTimer = new Timer(TIME_STEP, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();
    }

    private static void gameOver(UiFrame frame, KafkaProducerBombExplosion kafkaProducerBombExplosion) {
        frame.dispose();
        UiGameOverFrame game_over_frame = new UiGameOverFrame("GAME OVER", frame.getScoreBoard());
        game_over_frame.repaint();
        clockTimer.stop();
        kafkaProducerBombExplosion.close();
    }

    private static void tick(UiFrame frame, GameMapInitializer floor, KafkaProducerBombExplosion kafkaProducerBombExplosion) throws InterruptedException {
        if (floor.getIsGameOver()) {
            gameOver(frame, kafkaProducerBombExplosion);
        }
        else {
            frame.getUiComponent().repaint();
        }
    }

    private static void initialiseScoreboardProject(int numberOfCrates){
        KafkaProducerGameInitializer kafkaProducerGameInitializer = new KafkaProducerGameInitializer();

        GameStartData gameStartData = new GameStartData();
        gameStartData.pointsForBox = 10;
        gameStartData.livesPerPlayer = 3;
        gameStartData.numberOfBoxes = numberOfCrates;
        gameStartData.numberOfPlayers = 4;
        gameStartData.pointsForLife = 50;

        try {
            kafkaProducerGameInitializer.send(gameStartData);
        } catch (Exception e){
            System.out.println(e);
        }

        kafkaProducerGameInitializer.close();
    }
}
