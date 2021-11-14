package frontend.game_components;

import action_and_validation_tracker.ActionTracker;
import player_and_bomb_tracker.Bomb;
import frontend.UI.UiComponent;
import map_tracker.GameMapInitializer;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Player extends GameObject {

    private final static int PLAYER_PIXELS_BY_STEP = 4;
    private GameMapInitializer floor;
    private ActionTracker actionTracker = new ActionTracker();

    public Action up = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            actionTracker.movePlayer(Move.UP, Player.this, floor);

        }
    };

    public Action right = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            actionTracker.movePlayer(Move.RIGHT, Player.this, floor);

        }
    };

    public Action down = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            actionTracker.movePlayer(Move.DOWN, Player.this, floor);

        }
    };

    public Action left = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            actionTracker.movePlayer(Move.LEFT, Player.this, floor);

        }
    };


    public Action dropBomb = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e) {
            actionTracker.dropBomb(getRowIndex(), getColIndex(), floor);
        }
    };

    public Player(int PLAYER_START_X, int PLAYER_START_Y, GameMapInitializer floor) {
        super(PLAYER_START_X, PLAYER_START_Y, PLAYER_PIXELS_BY_STEP);
        this.floor = floor;
    }

    public void setPlayerButtons(char r, char l, char u, char d, char placeBomb, UiComponent uiComponent){ //set action_tracker => needs to be changed
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(r), "moveRight");
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(l), "moveLeft");
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(u), "moveUp");
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(d), "moveDown");
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(placeBomb), "dropBomb");
        uiComponent.getActionMap().put("moveRight", right);
        uiComponent.getActionMap().put("moveLeft", left);
        uiComponent.getActionMap().put("moveUp", up);
        uiComponent.getActionMap().put("moveDown", down);
        uiComponent.getActionMap().put("dropBomb", dropBomb);
    }

    public void setPlayerButtons(String r, String l, String u, String d, String placeBomb, UiComponent uiComponent){ //set action_tracker => needs to be changed
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(r), "moveRight");
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(l), "moveLeft");
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(u), "moveUp");
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(d), "moveDown");
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(placeBomb), "dropBomb");
        uiComponent.getActionMap().put("moveRight", right);
        uiComponent.getActionMap().put("moveLeft", left);
        uiComponent.getActionMap().put("moveUp", up);
        uiComponent.getActionMap().put("moveDown", down);
        uiComponent.getActionMap().put("dropBomb", dropBomb);
    }

    public void playerMoveBack(Move move) {
        moveBack(move);
    }

    public void playerMove(Move move) {
        move(move);
    }
}
