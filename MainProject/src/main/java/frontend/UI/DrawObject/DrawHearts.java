package frontend.UI.DrawObject;

import map_tracker.GameMapInitializer;

import java.awt.*;
import java.util.ArrayList;

public class DrawHearts {

    private ArrayList<Integer> playersHearts = new ArrayList<>();

    public DrawHearts(){
        for(int i = 0; i < 4; i++) {
            playersHearts.add(3);
        }
    }

    public void drawHearts(Graphics2D g2d, GameMapInitializer gameMapInitializer, int SQUARE_SIZE) {
        System.out.println("Player 0 has " + playersHearts.get(0) + " hearts");

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.PLAIN, 60);
        g2d.setFont(font);

        g2d.drawString("Lives:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 320);

        font = new Font("Serif", Font.PLAIN, 30);
        g2d.setFont(font);

        g2d.drawString("Player 1:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 400);
        this.hearts(playersHearts.get(0), g2d, gameMapInitializer, SQUARE_SIZE, 400);

        g2d.drawString("Player 2:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 440);
        this.hearts(playersHearts.get(1), g2d, gameMapInitializer, SQUARE_SIZE, 440);

        g2d.drawString("Player 3:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 480);
        this.hearts(playersHearts.get(2), g2d, gameMapInitializer, SQUARE_SIZE, 480);

        g2d.drawString("Player 4:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 520);
        this.hearts(playersHearts.get(3), g2d, gameMapInitializer, SQUARE_SIZE, 520);
    }

    private void hearts(int Player, Graphics2D g2d, GameMapInitializer gameMapInitializer, int SQUARE_SIZE, int Y) {
        for(int i = 0;  i< Player; i++) {
            g2d.drawString("\u2665", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE*(3+i + 1), Y);
        }
    }

    public void setPlayersHearts(ArrayList<Integer> playersHearts) {
        this.playersHearts = playersHearts;
    }

    public void setPlayerHeartsById(int playerId, int playersHearts) {
        this.playersHearts.set(playerId, playersHearts);
    }

    public int getPlayerHeartsById(int playerId, int playersHearts) {
        return this.playersHearts.get(playerId);
    }
}
