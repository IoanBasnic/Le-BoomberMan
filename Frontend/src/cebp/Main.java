package cebp;

import cebp.controllers.GameScreen;
import cebp.utils.GameMap;
import cebp.utils.Player;

public class Main {

    // Constants
    private static final int SCREEN_WIDTH = 30; // Columns
    private static final int SCREEN_HEIGHT = 30; // Rows

    private static final int PLAYER_START_1_X =    1;
    private static final int PLAYER_START_1_Y =    1;

    private static final int PLAYER_START_2_X =    SCREEN_HEIGHT-1;
    private static final int PLAYER_START_2_Y =    1;

    private static final int PLAYER_START_3_X =    1;
    private static final int PLAYER_START_3_Y =    SCREEN_WIDTH-1;

    private static final int PLAYER_START_4_X =    SCREEN_HEIGHT-1;
    private static final int PLAYER_START_4_Y =    SCREEN_WIDTH-1;

    public static void main(String[] args) {

        // Init screen
        GameScreen screen = new GameScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
        screen.InitScreen();

        // Init walls
        GameMap wall = new GameMap('#');
        wall.addWallsRow(screen, wall, 0); // First row
        wall.addWallsRow(screen, wall, screen.getScreenHeight() - 1); // Last


        wall.addWallsColumn(screen, wall, 0); // First column
        wall.addWallsColumn(screen, wall, screen.getScreenWidth() - 1); // Last

        // Init players
        Player player_1 = new Player('@', PLAYER_START_1_X, PLAYER_START_1_Y);
        screen.setObjectOnLocation(player_1, player_1.getX(), player_1.getY());

        Player player_2 = new Player('@', PLAYER_START_2_X, PLAYER_START_2_Y);
        screen.setObjectOnLocation(player_2, player_2.getX(), player_2.getY());

        Player player_3 = new Player('@', PLAYER_START_3_X, PLAYER_START_3_Y);
        screen.setObjectOnLocation(player_3, player_3.getX(), player_3.getY());

        Player player_4 = new Player('@', PLAYER_START_4_X, PLAYER_START_4_Y);
        screen.setObjectOnLocation(player_4, player_4.getX(), player_4.getY());
    }
}
