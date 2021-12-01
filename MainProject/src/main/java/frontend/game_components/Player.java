package frontend.game_components;

import action_and_validation_tracker.ActionTracker;
import frontend.UI.UiComponent;
import map_tracker.GameMapInitializer;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Player extends GameObject {

    private final static int PLAYER_PIXELS_BY_STEP = 4;
    private GameMapInitializer floor;
    private ActionTracker actionTracker = new ActionTracker();
    private String name;

//    public Action up = new AbstractAction() {
//        public void actionPerformed(ActionEvent e) {
//            System.out.println("UP PRESSED by " + name);
//            actionTracker.movePlayer(Move.UP, Player.this, floor);
//
//        }
//    };
//
//    public Action right = new AbstractAction() {
//        public void actionPerformed(ActionEvent e) {
//            System.out.println("FIRED");
//            actionTracker.movePlayer(Move.RIGHT, Player.this, floor);
//
//        }
//    };
//
//    public Action down = new AbstractAction() {
//        public void actionPerformed(ActionEvent e) {
//            actionTracker.movePlayer(Move.DOWN, Player.this, floor);
//
//        }
//    };
//
//    public Action left = new AbstractAction() {
//        public void actionPerformed(ActionEvent e) {
//            actionTracker.movePlayer(Move.LEFT, Player.this, floor);
//
//        }
//    };
//
    public Action dropBomb = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e) {
            actionTracker.dropBomb(getRowIndex(), getColIndex(), floor);
        }
    };

    public Player(int PLAYER_START_X, int PLAYER_START_Y, GameMapInitializer floor, String name) {
        super(PLAYER_START_X, PLAYER_START_Y, PLAYER_PIXELS_BY_STEP);
        this.floor = floor;
        this.name = name;
    }

    public void playerMoveBack(Move move, int deltaX, int deltaY) {
        moveBack(move, deltaX, deltaY, floor);
    }

    public void playerMove(int deltaX, int deltaY) {
        move(deltaX, deltaY);
    }
    public String getName(){
        return this.name;
    }
}
