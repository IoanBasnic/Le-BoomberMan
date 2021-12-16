package frontend.UI.DrawObject;

import map_tracker.GameMapInitializer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawScoreboard {

    private ArrayList<Integer> playersScores = new ArrayList<>();
    private ArrayList<String> playerNames = new ArrayList<>();

    private boolean namesSet = false;

    public DrawScoreboard(){
        for(int i = 0; i < 4; i++) {
            playersScores.add(0);
            playerNames.add("Player " + Integer.toString(i + 1));
        }
    }

    public void drawScoreboard(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.PLAIN, 60);
        g2d.setFont(font);
        g2d.setColor(Color.RED.brighter());

        g2d.drawString("Scoreboard:", 20, 60);

        font = new Font("Serif", Font.PLAIN, 30);
        g2d.setFont(font);

        g2d.drawString(playerNames.get(0), 20, 120);
        g2d.drawString(playersScores.get(0).toString(), 300, 120);

        g2d.drawString(playerNames.get(1), 20, 160);
        g2d.drawString(playersScores.get(1).toString(), 300, 160);

        g2d.drawString(playerNames.get(2), 20, 200);
        g2d.drawString(playersScores.get(2).toString(), 300, 200);

        g2d.drawString(playerNames.get(3), 20, 240);
        g2d.drawString(playersScores.get(3).toString(), 300, 240);
    }

    public void drawScoreboard(Graphics2D g2d, GameMapInitializer gameMapInitializer, int SQUARE_SIZE) {
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

        g2d.drawString("Scoreboard:", gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 60);

        font = new Font("Serif", Font.PLAIN, 30);
        g2d.setFont(font);

        g2d.drawString(playerNames.get(0), gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 120);
        g2d.drawString(playersScores.get(0).toString(), gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE*5, 120);

        g2d.drawString(playerNames.get(1), gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 160);
        g2d.drawString(playersScores.get(1).toString(), gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE*5, 160);

        g2d.drawString(playerNames.get(2), gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 200);
        g2d.drawString(playersScores.get(2).toString(), gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE*5, 200);

        g2d.drawString(playerNames.get(3), gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE, 240);
        g2d.drawString(playersScores.get(3).toString(), gameMapInitializer.getWidth() * SQUARE_SIZE + SQUARE_SIZE*5, 240);
    }

    public void setPlayersScores(ArrayList<Integer> playersScores) {
        this.playersScores = playersScores;
    }

    public ArrayList<Integer> getPlayersScores() {
        return playersScores;
    }

    public void setPlayerScoreById(int playerId, int playersScore) {
        this.playersScores.set(playerId, playersScore);
    }

    public int getPlayerScoreById(int playerId) {
        return this.playersScores.get(playerId);
    }

    public void setPlayerNameById(int playerId, String name) {
        this.playerNames.set(playerId, name);
    }

    public String getPlayerNameById(int playerId) {
        return this.playerNames.get(playerId);
    }

    public boolean isNamesSet() {
        return namesSet;
    }

    public void setNamesSet(boolean namesSet) {
        this.namesSet = namesSet;
    }
}