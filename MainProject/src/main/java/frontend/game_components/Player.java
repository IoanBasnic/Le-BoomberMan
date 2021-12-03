package frontend.game_components;

import action_and_validation_tracker.ActionTracker;
import frontend.UI.UiComponent;
import map_tracker.GameMapInitializer;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Player extends GameObject {

    private final static int PLAYER_PIXELS_BY_STEP = 4;
    private boolean isAlive;
    private GameMapInitializer floor;
    private ActionTracker actionTracker = new ActionTracker();
    private String name;
    private int bombsPlaced = 0;
    private int noLifes;
    private boolean hit = false;
    private boolean invincible;
    private int timeInvincible = 150;

    public void setHit(boolean hit){
        this.hit = hit;
    }
    public boolean hitted(){
        return hit;
    }

    public int getNoLifes(){
        return this.noLifes;
    }

    public int getTimeInvincible(){
        return this.timeInvincible;
    }
    public void setTimeInvincible(int timeInvincible) {
        this.timeInvincible = timeInvincible;
    }

    public boolean IsAlive() {
        return isAlive;
    }
    public void setAlive(){
        this.isAlive = true;
    }

    public void setInvincible(boolean invincible){this.invincible = invincible;}
    public boolean isInvincible() {return invincible;}

    public void setDead(){
        noLifes--;
        System.out.println("Lifes " + noLifes);
        if(noLifes < 1){
            this.isAlive = false;
        }
    }

    public int getBombsPlaced(){
        return this.bombsPlaced;
    }

    public void updateNoBombs(){
        bombsPlaced++;
    }

    public void removeBomb(){
        bombsPlaced--;
    }

    public Player(int PLAYER_START_X, int PLAYER_START_Y, GameMapInitializer floor, String name) {
        super(PLAYER_START_X, PLAYER_START_Y, PLAYER_PIXELS_BY_STEP);
        this.floor = floor;
        this.name = name;
        this.noLifes = 3;
        this.invincible = false;
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
