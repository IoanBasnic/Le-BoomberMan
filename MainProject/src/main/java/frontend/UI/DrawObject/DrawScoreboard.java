package frontend.UI.DrawObject;

import map_tracker.GameMapInitializer;

import java.awt.*;
import java.util.ArrayList;

public class DrawScoreboard {

    private ArrayList<Integer> playersScores = new ArrayList<>();

    public DrawScoreboard(){
        for(int i = 0; i < 4; i++) {
            playersScores.add(0);
        }
    }

    public void drawScoreboard(Graphics2D g2d, GameMapInitializer gameMapInitializer, int SQUARE_SIZE) {
        System.out.println("Player 0 has " + playersScores.get(0) + " score");

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.PLAIN, 60);
        g2d.setFont(font);

        g2d.drawString("Scoreboard:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 60);

        font = new Font("Serif", Font.PLAIN, 30);
        g2d.setFont(font);

        g2d.drawString("Player 1:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 120);
        g2d.drawString(playersScores.get(0).toString(), gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE*5, 120);

        g2d.drawString("Player 2:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 160);
        g2d.drawString(playersScores.get(1).toString(), gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE*5, 160);

        g2d.drawString("Player 3:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 200);
        g2d.drawString(playersScores.get(2).toString(), gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE*5, 200);

        g2d.drawString("Player 4:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 240);
        g2d.drawString(playersScores.get(3).toString(), gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE*5, 240);
    }

    public void setPlayersScores(ArrayList<Integer> playersScores) {
        this.playersScores = playersScores;
    }

    public void setPlayerScoreById(int playerId, int playersScore) {
        this.playersScores.set(playerId, playersScore);
    }

    public int getPlayerScoreById(int playerId) {
        return this.playersScores.get(playerId);
    }
}