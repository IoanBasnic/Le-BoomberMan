package cebp;

import cebp.controllers.GameScreen;
import cebp.utils.GameMap;

public class Main {

    // Constants
    private static final int SCREEN_WIDTH = 30; // Columns
    private static final int SCREEN_HEIGHT = 30; // Rows

    private static final int PLAYER_START_1_X =    0;
    private static final int PLAYER_START_1_Y =    0;

    private static final int PLAYER_START_2_X =    SCREEN_HEIGHT;
    private static final int PLAYER_START_2_Y =    0;

    private static final int PLAYER_START_3_X =    0;
    private static final int PLAYER_START_3_Y =    SCREEN_WIDTH;

    private static final int PLAYER_START_4_X =    SCREEN_HEIGHT;
    private static final int PLAYER_START_4_Y =    SCREEN_WIDTH;

    public static void main(String[] args) {

        // Init screen
        GameScreen screen = new GameScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
        screen.InitScreen();

        // Init walls
        GameMap wall = new GameMap('#');
        wall.addWallsRow(screen, wall, 0); // First row
        wall.addWallsRow(screen, wall, screen.getScreenHeight() - 1); // Last
        // row
        wall.addWallsColumn(screen, wall, 0); // First column
        wall.addWallsColumn(screen, wall, screen.getScreenWidth() - 1); // Last
    }
}
