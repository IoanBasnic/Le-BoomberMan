import frontend.game_components.Crates;
import map_tracker.GameScreen;
import map_tracker.GameMapInitializer;
import frontend.game_components.Player;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    // Constants
    private static final int SCREEN_WIDTH = 60; // Columns
    private static final int SCREEN_HEIGHT = 15; // Rows

    private static final int PLAYER_START_1_X =    1;
    private static final int PLAYER_START_1_Y =    1;

    private static final int PLAYER_START_2_X =    SCREEN_WIDTH-2;
    private static final int PLAYER_START_2_Y =    1;

    private static final int PLAYER_START_3_X =    1;
    private static final int PLAYER_START_3_Y =    SCREEN_HEIGHT-2;

    private static final int PLAYER_START_4_X =    SCREEN_WIDTH-2;
    private static final int PLAYER_START_4_Y =    SCREEN_HEIGHT-2;

    public static void main(String[] args) throws IOException {

        // Init screen
        GameScreen screen = new GameScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
        screen.InitScreen();

        // Init walls
        GameMapInitializer wall = new GameMapInitializer('#');
        wall.addWallsRow(screen, wall, 0); // First row
        wall.addWallsRow(screen, wall, screen.getScreenHeight() - 1); // Last


        wall.addWallsColumn(screen, wall, 0); // First column
        wall.addWallsColumn(screen, wall, screen.getScreenWidth() - 1); // Last

        // Init players
        Player player_1 = new Player('1', PLAYER_START_1_X, PLAYER_START_1_Y);
        screen.setObjectOnLocation(player_1, player_1.getX(), player_1.getY());

        // Init crates
        Crates crates = new Crates('▢');
        Crates.addRandomCrates(screen, crates);

        //use this method in GameScreen
        //public synchronized void explodeBomb(bombLocation, radius) {
            // some thread critical stuff
            // here
        //}

        bombTracker = BombTracker.createBombTracker();
        //bombTracker calls explodeBomb when the sleep from the bomb thread comes to an end

        playerMoveValidator = PlayerMoveValidator(bombTracker)

        // Input from player
        Scanner scanner = new Scanner(System.in);
        char input;

        // The game logic starts here
        boolean isRunning = true;

        while (isRunning) {
            screen.PrintScreen();
            // Get input from player and do something
            input = scanner.nextLine().charAt(0);
            processAndValidatePlayerMove(input, player_1);

            switch (input = scanner.nextLine().charAt(0)) {



                case 'a':
                    player_1.MoveLeft(screen, player_1);
                    break;
                case 'd':
                    player_1.MoveRight(screen, player_1);
                    break;
                case 'w':
                    player_1.MoveUp(screen, player_1);
                    break;
                case 's':
                    player_1.MoveDown(screen, player_1);
                    break;

                case 'q':
                    isRunning = false;
            }
        }
    }
}
