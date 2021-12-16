package player_and_bomb_tracker;

import frontend.game_components.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Bomb
{
    // Constants are static by definition.
    private final static int BOMBSIZE = 30;
    private int timeToExplosion = 5000;
    private final int rowIndex;
    private final int colIndex;
    private int explosionRadius;
    private List<Boolean> playersLeft;
    private final Player player;

    public Bomb(final int rowIndex, final int colIndex, final Player player) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.explosionRadius = 1;
        this.player = player;
        playersLeft = new ArrayList<Boolean>(Arrays.asList(new Boolean[4]));
        Collections.fill(playersLeft, Boolean.TRUE);

        playersLeft.set(player.getId(), Boolean.FALSE);
    }

    public Player getPlayer() {
        return player;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    // This method is static since every bomb has the same size.
    public static int getBOMBSIZE() {
        return BOMBSIZE;
    }

    public int getTimeToExplosion() {
        return timeToExplosion;
    }

    public void setTimeToExplosion(final int timeToExplosion) {
        this.timeToExplosion = timeToExplosion;
    }

    public int getExplosionRadius() {
        return explosionRadius;
    }

    public boolean isPlayerLeft(Integer playerId) {
        return playersLeft.get(playerId);
    }

    public void setPlayerLeft(final boolean playerLeft, Integer playerId) {
        this.playersLeft.set(playerId, playerLeft);
    }
}