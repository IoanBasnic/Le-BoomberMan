import frontend.UI.UiFrame;
import map_tracker.GameMapInitializer;

import javax.swing.*;
import java.awt.*;
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
        GameMapInitializer floor = new GameMapInitializer(width, height);
        UiFrame frame = new UiFrame("Le Boomberman", floor);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        floor.addFloorListener(frame.getUiComponent());

        Thread repaintThread = new Thread(frame.getUiComponent());
        repaintThread.start();

        Action doOneStep = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e) {
                tick(frame, floor);
            }
        };
        clockTimer = new Timer(TIME_STEP, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();
    }

    private static void gameOver(UiFrame frame, GameMapInitializer floor) {
        clockTimer.stop();
        frame.dispose();
        startGame();
    }

    private static void tick(UiFrame frame, GameMapInitializer floor) {
        if (floor.getIsGameOver()) {
            gameOver(frame, floor);
        } else {
            floor.bombCountdown();
            floor.explosionHandler();
            floor.characterInExplosion();
        }
    }
}
