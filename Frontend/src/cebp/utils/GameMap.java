package cebp.utils;

import cebp.controllers.GameObject;
import cebp.controllers.GameScreen;

public class GameMap extends GameObject {

    public GameMap() {
        setSymbol('#');
    }

    public GameMap(char symbol) {
        setSymbol(symbol);
    }

    // Add horizontal line of walls
    public void addWallsRow(GameScreen screen, GameMap wall, int rowNumber) {
        for (int i = 0; i < screen.getScreenWidth(); i++) {
            screen.setObjectOnLocation(wall, i, rowNumber);
        }
    }

    // Add vertical line of walls
    public void addWallsColumn(GameScreen screen, GameMap wall, int columnNumber) {
        for (int i = 0; i < screen.getScreenHeight(); i++) {
            screen.setObjectOnLocation(wall, columnNumber, i);
        }
    }

}
