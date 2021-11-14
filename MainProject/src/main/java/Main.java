import frontend.UI.UiFrame;
import map_tracker.GameMapInitializer;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Main {
    private static final int TIME_STEP = 30;
    private static Timer clockTimer = null;

    private Main() {}

    public static void main(String[] args) {
        startGame();
    }

    private static void startGame() {
        int width = 20;
        int height = 20;
        GameMapInitializer floor = new GameMapInitializer(width, height);
        UiFrame frame = new UiFrame("Le Boomberman", floor);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        floor.addFloorListener(frame.getUiComponent());

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
            floor.notifyListeners();
        }
    }
}
