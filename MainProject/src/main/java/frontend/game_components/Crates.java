package frontend.controllers;

import map_tracker.GameScreen;

public class Crates extends GameObject {

    public Crates(char symbol) {
        setSymbol(symbol);
    }

    // Add crate to random location inside the matrix limits
    public static void addRandomCrates(GameScreen screen, Crates crates) {
        int counter = 0;
        while(counter < 50) {
            screen.setObjectOnLocation(crates, (int) (Math.random() * (screen.getScreenWidth() - 1)),
                    (int) (Math.random() * (screen.getScreenHeight() - 1)));
            counter++;
        }
    }

}
