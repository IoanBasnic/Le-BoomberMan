package frontend.game_components;

import map_tracker.GameMapInitializer;

public class Player extends GameObject {

    private final static int PLAYER_PIXELS_BY_STEP = 4;
    private Integer id;
    private boolean isAlive;
    private GameMapInitializer floor;
    private String name;
    private int bombsPlaced = 0;
    private int noLifes;
    private boolean hit = false;
    private boolean invincible;
    private int cratesDestroyed = 0;

    public void setHit(boolean hit){
        this.hit = hit;
    }
    public boolean isHit(){
        return hit;
    }

    public void kill(){
        this.isAlive = false;
    }

    public int getNoLifes(){
        return this.noLifes;
    }


    public boolean IsAlive() {
        return isAlive;
    }
    public void setAlive(){
        this.isAlive = true;
    }

    public void setInvincible(boolean invincible){this.invincible = invincible;}
    public boolean isInvincible() {return invincible;}

    public void updateCratesDestroyed(){
        cratesDestroyed++;
    }
    public int getCratesDestroyed(){
        return cratesDestroyed;
    }

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

    public Player(int PLAYER_START_X, int PLAYER_START_Y, GameMapInitializer floor, String name, Integer id) {
        super(PLAYER_START_X, PLAYER_START_Y, PLAYER_PIXELS_BY_STEP);
        this.floor = floor;
        this.name = name;
        this.noLifes = 3;
        this.invincible = false;
        this.id = id;
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

    public Integer getId() {
        return id;
    }
}
