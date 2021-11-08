package frontend.utils;

import frontend.controllers.GameObject;
import map_tracker.GameScreen;

public class GameMapInitializer extends GameObject {

    public GameMapInitializer() {
        setSymbol('#');
    }

    public GameMapInitializer(char symbol) {
        setSymbol(symbol);
    }

    // Add horizontal line of walls
    public void addWallsRow(GameScreen screen, GameMapInitializer wall, int rowNumber) {
        for (int i = 0; i < screen.getScreenWidth(); i++) {
            screen.setObjectOnLocation(wall, i, rowNumber);
        }
    }

    // Add vertical line of walls
    public void addWallsColumn(GameScreen screen, GameMapInitializer wall, int columnNumber) {
        for (int i = 0; i < screen.getScreenHeight(); i++) {
            screen.setObjectOnLocation(wall, columnNumber, i);
        }
    }

}
