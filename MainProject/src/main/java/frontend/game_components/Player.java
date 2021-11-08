package frontend.game_components;

import map_tracker.GameScreen;

public class Player extends GameObject {

    public Player(char symbol, int xStartingLocation, int yStartingLocation) {
        setSymbol(symbol);
        setX(xStartingLocation);
        setY(yStartingLocation);
    }

    public void MoveLeft(GameScreen screen, Player player) {
        player.setX(getX() - 1);
        screen.setObjectOnLocation(player, player.getX(), player.getY());
        screen.ClearScreenLocation(player.getX() + 1, player.getY());
    }

    public void MoveRight(GameScreen screen, Player player) {
        player.setX(getX() + 1);
        screen.setObjectOnLocation(player, player.getX(), player.getY());
        screen.ClearScreenLocation(player.getX() - 1, player.getY());
    }

    public void MoveUp(GameScreen screen, Player player) {
        player.setY(getY() - 1);
        screen.setObjectOnLocation(player, player.getX(), player.getY());
        screen.ClearScreenLocation(player.getX(), player.getY() + 1);
    }

    public void MoveDown(GameScreen screen, Player player) {
        player.setY(getY() + 1);
        screen.setObjectOnLocation(player, player.getX(), player.getY());
        screen.ClearScreenLocation(player.getX(), player.getY() - 1);
    }

    // WIP
    public void PutBomb(GameScreen screen, Player player) {
        screen.setObjectOnLocation(player, player.getX(), player.getY());
        screen.ClearScreenLocation(player.getX(), player.getY() - 1);
    }
}