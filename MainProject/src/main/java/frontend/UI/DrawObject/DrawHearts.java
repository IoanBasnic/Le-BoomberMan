package frontend.UI.DrawObject;

import map_tracker.GameMapInitializer;

import java.awt.*;

public class DrawHearts {
    private int player1Hearts = 3;
    private int player2Hearts = 3;
    private int player3Hearts = 3;
    private int player4Hearts = 3;

    public void drawHearts(Graphics2D g2d, GameMapInitializer gameMapInitializer, int SQUARE_SIZE) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.PLAIN, 60);
        g2d.setFont(font);

        g2d.drawString("Lives:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 320);

        font = new Font("Serif", Font.PLAIN, 30);
        g2d.setFont(font);

        g2d.drawString("Player 1:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 400);
        this.hearts(player1Hearts, g2d, gameMapInitializer, SQUARE_SIZE, 400);

        g2d.drawString("Player 2:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 440);
        this.hearts(player2Hearts, g2d, gameMapInitializer, SQUARE_SIZE, 440);

        g2d.drawString("Player 3:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 480);
        this.hearts(player3Hearts, g2d, gameMapInitializer, SQUARE_SIZE, 480);

        g2d.drawString("Player 4:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 520);
        this.hearts(player4Hearts, g2d, gameMapInitializer, SQUARE_SIZE, 520);
    }

    private void hearts(int Player, Graphics2D g2d, GameMapInitializer gameMapInitializer, int SQUARE_SIZE, int Y) {
        for(int i = 0;  i< Player; i++) {
            g2d.drawString("\u2665", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE*(3+i + 1), Y);
        }
    }

    public void setPlayer1Hearts(int player1Hearts) {
        this.player1Hearts = player1Hearts;
    }

    public void setPlayer2Hearts(int player2Hearts) {
        this.player2Hearts = player2Hearts;
    }

    public void setPlayer3Hearts(int player3Hearts) {
        this.player3Hearts = player3Hearts;
    }

    public void setPlayer4Hearts(int player4Hearts) {
        this.player4Hearts = player4Hearts;
    }
}
