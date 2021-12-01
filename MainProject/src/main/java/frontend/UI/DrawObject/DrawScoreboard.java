package frontend.UI.DrawObject;

import map_tracker.GameMapInitializer;

import java.awt.*;

public class DrawScoreboard {

    private String player1Score = "0";
    private String player2Score = "0";
    private String player3Score = "0";
    private String player4Score = "0";

    public void drawScoreboard(Graphics2D g2d, GameMapInitializer gameMapInitializer, int SQUARE_SIZE) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.PLAIN, 60);
        g2d.setFont(font);

        g2d.drawString("Scoreboard:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 60);

        font = new Font("Serif", Font.PLAIN, 30);
        g2d.setFont(font);

        g2d.drawString("Player 1:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 120);
        g2d.drawString(player1Score, gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE*5, 120);

        g2d.drawString("Player 2:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 160);
        g2d.drawString(player2Score, gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE*5, 160);

        g2d.drawString("Player 3:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 200);
        g2d.drawString(player3Score, gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE*5, 200);

        g2d.drawString("Player 4:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 240);
        g2d.drawString(player4Score, gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE*5, 240);
    }

    public void setPlayer1Score(String player1Score) {
        this.player1Score = player1Score;
    }

    public void setPlayer2Score(String player2Score) {
        this.player2Score = player2Score;
    }

    public void setPlayer3Score(String player3Score) {
        this.player3Score = player3Score;
    }

    public void setPlayer4Score(String player4Score) {
        this.player4Score = player4Score;
    }
}