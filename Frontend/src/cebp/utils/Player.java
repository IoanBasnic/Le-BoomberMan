package cebp.utils;

import cebp.controllers.GameObject;
import cebp.controllers.GameScreen;

public class Player extends GameObject {

    public Player(char symbol, int xStartingLocation, int yStartingLocation) {
        setSymbol(symbol);
        setX(xStartingLocation);
        setY(yStartingLocation);
    }

    public void moveLeft(GameScreen screen, Player player) {
        player.setX(getX() - 1);
        screen.setObjectOnLocation(player, player.getX(), player.getY());
        screen.ClearScreenLocation(player.getX() + 1, player.getY());
    }

    public void moveRight(GameScreen screen, Player player) {
        player.setX(getX() + 1);
        screen.setObjectOnLocation(player, player.getX(), player.getY());
        screen.ClearScreenLocation(player.getX() - 1, player.getY());
    }

    public void moveUp(GameScreen screen, Player player) {
        player.setY(getY() - 1);
        screen.setObjectOnLocation(player, player.getX(), player.getY());
        screen.ClearScreenLocation(player.getX(), player.getY() + 1);
    }

    public void moveDown(GameScreen screen, Player player) {
        player.setY(getY() + 1);
        screen.setObjectOnLocation(player, player.getX(), player.getY());
        screen.ClearScreenLocation(player.getX(), player.getY() - 1);
    }

}
