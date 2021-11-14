//package frontend.players;
//
//import frontend.game_components.Crates;
//import map_tracker.GameScreen;
//import map_tracker.GameMapInitializer;
//import frontend.game_components.Player;
//
//import java.io.IOException;
//import java.util.Scanner;
//
//public class Player2 {
//
//    // Constants
//    private static final int SCREEN_WIDTH = 60; // Columns
//    private static final int SCREEN_HEIGHT = 15; // Rows
//
//    private static final int PLAYER_START_1_X =    1;
//    private static final int PLAYER_START_1_Y =    1;
//
//    private static final int PLAYER_START_2_X =    SCREEN_WIDTH-2;
//    private static final int PLAYER_START_2_Y =    1;
//
//    private static final int PLAYER_START_3_X =    1;
//    private static final int PLAYER_START_3_Y =    SCREEN_HEIGHT-2;
//
//    private static final int PLAYER_START_4_X =    SCREEN_WIDTH-2;
//    private static final int PLAYER_START_4_Y =    SCREEN_HEIGHT-2;
//
//    public static void main(String[] args) throws IOException {
//
//        // Init screen
//        GameScreen screen = new GameScreen(SCREEN_WIDTH, SCREEN_HEIGHT);
//        screen.InitScreen();
//
//        // Init walls
//        GameMapInitializer wall = new GameMapInitializer('#');
//        wall.addWallsRow(screen, wall, 0); // First row
//        wall.addWallsRow(screen, wall, screen.getScreenHeight() - 1); // Last
//
//
//        wall.addWallsColumn(screen, wall, 0); // First column
//        wall.addWallsColumn(screen, wall, screen.getScreenWidth() - 1); // Last
//
//        // Init players
//        Player player_1 = new Player('@', PLAYER_START_1_X, PLAYER_START_1_Y);
//        screen.setObjectOnLocation(player_1, player_1.getX(), player_1.getY());
//
//        Player player_2 = new Player('@', PLAYER_START_2_X, PLAYER_START_2_Y);
//        screen.setObjectOnLocation(player_2, player_2.getX(), player_2.getY());
//
//        Player player_3 = new Player('@', PLAYER_START_3_X, PLAYER_START_3_Y);
//        screen.setObjectOnLocation(player_3, player_3.getX(), player_3.getY());
//
//        Player player_4 = new Player('@', PLAYER_START_4_X, PLAYER_START_4_Y);
//        screen.setObjectOnLocation(player_4, player_4.getX(), player_4.getY());
//
//        // Init crates
//        Crates crates = new Crates('*');
//        Crates.addRandomCrates(screen, crates);
//
//        // Input from player
//        Scanner scanner = new Scanner(System.in);
//        char input;
//
//        // The game logic starts here
//        boolean isRunning = true;
//
//        while (isRunning) {
//            screen.PrintScreen();
//            // Get input from player and do something
//            switch (input = scanner.nextLine().charAt(0)) {
//                case 'h':
//                    player_2.MoveLeft(screen, player_2);
//                    break;
//                case 'k':
//                    player_2.MoveRight(screen, player_2);
//                    break;
//                case 'u':
//                    player_2.MoveUp(screen, player_2);
//                    break;
//                case 'j':
//                    player_2.MoveDown(screen, player_2);
//                    break;
//
//
//            }
//        }
//    }
//}
