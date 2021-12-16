package frontend.UI.DrawObject;

import map_tracker.GameMapInitializer;

import java.awt.*;
import java.util.ArrayList;

public class DrawHearts {

    private ArrayList<Integer> playersHearts = new ArrayList<>();
    private ArrayList<String> playerNames = new ArrayList<>();

    private boolean namesSet = false;

    public DrawHearts(){
        for(int i = 0; i < 4; i++) {
            playersHearts.add(3);
            playerNames.add("Player " + Integer.toString(i + 1));
        }
    }

    public void drawHearts(Graphics2D g2d, GameMapInitializer gameMapInitializer, int SQUARE_SIZE) {
        if (!isNamesSet()){
            playerNames.set(0, gameMapInitializer.getPlayer1().getName());
            playerNames.set(1, gameMapInitializer.getPlayer2().getName());
            playerNames.set(2, gameMapInitializer.getPlayer3().getName());
            playerNames.set(3, gameMapInitializer.getPlayer4().getName());
            setNamesSet(true);
        }
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.PLAIN, 60);
        g2d.setFont(font);

        g2d.drawString("Lives:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 320);

        font = new Font("Serif", Font.PLAIN, 30);
        g2d.setFont(font);

        g2d.drawString(playerNames.get(0), gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 400);
        this.hearts(playersHearts.get(0), g2d, gameMapInitializer, SQUARE_SIZE, 400);

        g2d.drawString(playerNames.get(1), gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 440);
        this.hearts(playersHearts.get(1), g2d, gameMapInitializer, SQUARE_SIZE, 440);

        g2d.drawString(playerNames.get(2), gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 480);
        this.hearts(playersHearts.get(2), g2d, gameMapInitializer, SQUARE_SIZE, 480);

        g2d.drawString(playerNames.get(3), gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 520);
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
    public ArrayList<Integer> getPlayersHearts() {
        return this.playersHearts;
    }

    public void setPlayerHeartsById(int playerId, int playersHearts) {
        this.playersHearts.set(playerId - 1, playersHearts);
    }

    public int getPlayerHeartsById(int playerId) {
        return this.playersHearts.get(playerId);
    }

    public boolean isNamesSet() {
        return namesSet;
    }

    public void setNamesSet(boolean namesSet) {
        this.namesSet = namesSet;
    }
}
