package map_tracker;

import action_and_validation_tracker.ActionTracker;
import frontend.UI.DrawObject.DrawHearts;
import player_and_bomb_tracker.Bomb;
import player_and_bomb_tracker.BombExplosion;
import frontend.UI.UiComponent;
import frontend.game_components.Player;

import javax.swing.*;
import java.util.*;


public class GameMapInitializer {
    private final static double CHANCE_FOR_BREAKABLE_BLOCK = 0.5;
    private final BlockEntityEnum[][] tiles;
    private static int PLAYER_X_START = 60;
    private static int PLAYER_Y_START = 60;
    private int width;
    private int height;
    private UiComponent mapListener;
    private Player player1 = null;
    private Player player2 = null;
    private Player player3 = null;
    private Player player4 = null;
    private int index;
    private int playersAlive = 4;
    private boolean exploded = false;
    private int cratesExploded = 0;

    private List<Bomb> bombList= new ArrayList<>();
    private Collection<Bomb> explosionList= new ArrayList<>();
    private Collection<BombExplosion> bombExplosionCoords = new ArrayList<>();
    private List<Player> exp = new ArrayList<>();
    private List<Player> playersList = new ArrayList<>();

    public GameMapInitializer(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new BlockEntityEnum[height][width];
        placeBreakable();
        placeUnbreakableAndGrass();
    }

    public static int pixelToSquare(int pixelCoord){
        return ((pixelCoord + UiComponent.getSquareSize()-1) / UiComponent.getSquareSize())-1;
    }

    public BlockEntityEnum getFloorTile(int rowIndex, int colIndex) {
        return tiles[rowIndex][colIndex];
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2() {
        return player2;
    }
    public Player getPlayer3() {
        return player3;
    }
    public Player getPlayer4() {
        return player4;
    }

    public Iterable<Bomb> getBombList() {
        return bombList;
    }

    public int getBombListSize() {
        return bombList.size();
    }

    public Iterable<BombExplosion> getBombExplosionCoords() {
        return bombExplosionCoords;
    }

    public boolean getIsGameOver() {
        if(playersAlive < 3){
            try {
                for(int i = 0; i < 5; i++){
                    explosionHandler();
                    mapListener.UpdateMap();
                }
                Thread.sleep(2000);
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void addToBombList(Bomb bomb, Player player) {
        player.updateNoBombs();
        bombList.add(bomb);
    }

    public void createPlayer(UiComponent uiComponent){
        player1 = new Player(PLAYER_X_START, PLAYER_Y_START, this, "Player1");
        player2 = new Player(PLAYER_X_START * 12+20, PLAYER_Y_START, this,"Player2");
        player3 = new Player(PLAYER_X_START, PLAYER_Y_START * 12+20, this,"Player3");
        player4 = new Player(PLAYER_X_START * 12-20, PLAYER_Y_START * 12+20, this,"Player4");

        playersList.add(player1);
        playersList.add(player2);
        playersList.add(player3);
        playersList.add(player4);

        for(Player player : playersList){
            player.setAlive();
        }

        ActionTracker a = new ActionTracker();
        a.setKeys(uiComponent, this, player1, player2, player3, player4);
    }

    public int squareToPixel(int squareCoord){
        return squareCoord * UiComponent.getSquareSize();
    }

    public void addFloorListener(UiComponent bl) {
        mapListener = bl;
    }

    public UiComponent getUi(){
        return mapListener;
    }

    public void notifyListeners() {
        getUi().UpdateMap();
    }

    /**
     * This method creates a bomb if the given demands are satisfied.
     */
    public void bombCountdown() {
        Collection<Integer> bombIndexesToBeRemoved = new ArrayList<>();
        explosionList.clear();
        index = 0;
        for (Bomb b : bombList) {
            b.setTimeToExplosion(b.getTimeToExplosion() - 1);
            //System.out.println("Bomb: " + b.getPlayer().getName() + "  " + b.getTimeToExplosion());
            if (b.getTimeToExplosion() == 0) {
                b.getPlayer().removeBomb();
                bombIndexesToBeRemoved.add(index);
                //System.out.println("INDEX " + index + " Bomb: " + b.getPlayer().getName());
                explosionList.add(b);
                //Explosion(b);
            }
            index++;
        }
        for (int i : bombIndexesToBeRemoved) {
            try {
                bombList.remove(i);
            } catch (IndexOutOfBoundsException e) {
                bombList.remove(i-1);
                System.out.println("CATCHED " + i);
                System.out.println("SIZE " + bombIndexesToBeRemoved.size());
            }
        }
    }

    public void explosionHandler(){
        Collection<BombExplosion> explosionsToBeRemoved = new ArrayList<>();

        for (BombExplosion e: bombExplosionCoords) {
            if(e.getDuration() == 4){
                exploded = true;
            }

            e.setDuration(e.getDuration()-1);
            if(e.getDuration()==0){
                explosionsToBeRemoved.add(e);
                for(Player player : playersList){
                    player.setHit(false);
                }
            }
        }
        for (BombExplosion e: explosionsToBeRemoved){
            exploded = false;
            bombExplosionCoords.remove(e);}

        for(Bomb e : explosionList) {
            int eRow = e.getRowIndex();
            int eCol = e.getColIndex();
            boolean northOpen = true;
            boolean southOpen = true;
            boolean westOpen = true;
            boolean eastOpen = true;
            bombExplosionCoords.add(new BombExplosion(eRow, eCol, e));
            for (int i = 1; i < e.getExplosionRadius() + 1; i++) {
                if (eRow - i >= 0 && northOpen) {
                    northOpen = bombCoordinateCheck(eRow - i, eCol, northOpen, e);
                }
                if (eRow - i <= height && southOpen) {
                    southOpen = bombCoordinateCheck(eRow + i, eCol, southOpen, e);
                }
                if (eCol - i >= 0 && westOpen) {
                    westOpen = bombCoordinateCheck(eRow, eCol - i, westOpen, e);
                }
                if (eCol + i <= width && eastOpen) {
                    eastOpen = bombCoordinateCheck(eRow, eCol + i, eastOpen, e);
                }
            }
        }
    }

    public void playerHit(Player player, int PLAYER_X_START2, int PLAYER_Y_START2, int playerId){
        player.setHit(true);
        player.setDead();
        player.setInvincible(true);
        player.setX(PLAYER_X_START2);
        player.setY(PLAYER_Y_START2);
        mapListener.getDrawHearts().setPlayerHeartsById(player.getNoLifes(), playerId);
        if(!player.IsAlive()){
            playersAlive--;
        }
    }

    public void killPlayer(int playerId){
        if(playerId == 0){
            player1.kill();
        }
        else if(playerId == 1){
            player2.kill();
        }
        else if(playerId == 2){
            player3.kill();
        }
        else if(playerId == 3){
            player4.kill();
        }
    }

    public void playerInExplosion(){
        for (BombExplosion tup: bombExplosionCoords) {
            if(player1.IsAlive() && !player1.isHit() && collidingCircles(player1, squareToPixel(tup.getColIndex()), squareToPixel(tup.getRowIndex()))){
                if(!player1.isInvincible()){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    playerHit(player1, PLAYER_X_START, PLAYER_Y_START, 1);

                    if(exploded == false){
                        exp.add(tup.getBomb().getPlayer());
                        System.out.println("PLAYER 1 was killed by " + tup.getBomb().getPlayer().getName());
                    }
                }
                else{
                    System.out.println("PLAYER 1 IS INVINCIBLE");
                }
            }// PLAYER 1 END
            if(player2.IsAlive() && !player2.isHit() && collidingCircles(player2, squareToPixel(tup.getColIndex()), squareToPixel(tup.getRowIndex()))){
                if(!player2.isInvincible()){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    playerHit(player2, PLAYER_X_START * 12+20, PLAYER_Y_START, 2);

                    if(exploded == false){
                        exp.add(tup.getBomb().getPlayer());
                        System.out.println("PLAYER 2 was killed by " + tup.getBomb().getPlayer().getName());
                    }
                }
                else{
                    System.out.println("PLAYER 2 IS INVINCIBLE");
                }
            }// PLAYER 2 END
            if(player3.IsAlive() && !player3.isHit() && collidingCircles( player3, squareToPixel(tup.getColIndex()), squareToPixel(tup.getRowIndex()) )) {
                if (!player3.isInvincible()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    playerHit(player3, PLAYER_X_START, PLAYER_Y_START * 12 + 20, 3);

                    if (exploded == false) {
                        exp.add(tup.getBomb().getPlayer());
                        System.out.println("PLAYER 3 was killed by " + tup.getBomb().getPlayer().getName());
                    }
                } else {
                    System.out.println("PLAYER 3 IS INVINCIBLE");
                }
            }// PLAYER 3 END
            if(player4.IsAlive() && !player4.isHit() && collidingCircles(player4, squareToPixel(tup.getColIndex()), squareToPixel(tup.getRowIndex()))){
                if(!player4.isInvincible()){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    playerHit(player4, PLAYER_X_START * 12-20, PLAYER_Y_START * 12+20, 4);

                    if(exploded == false){
                        exp.add(tup.getBomb().getPlayer());
                        System.out.println("PLAYER 4 was killed by " + tup.getBomb().getPlayer().getName());
                    }
                }
                else{
                    System.out.println("PLAYER 4 IS INVINCIBLE");
                }
            }
        }
    }

    private void placeBreakable () {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                double r = Math.random();
                if (r < CHANCE_FOR_BREAKABLE_BLOCK) {
                    tiles[i][j] = BlockEntityEnum.CRATE;
                }
            }
        }
        clearSpawn();
    }

    private void clearSpawn () {
        tiles[1][1] = BlockEntityEnum.GRASS;
        tiles[1][2] = BlockEntityEnum.GRASS;
        tiles[2][1] = BlockEntityEnum.GRASS;

        tiles[1][width-2] = BlockEntityEnum.GRASS;
        tiles[1][width-3] = BlockEntityEnum.GRASS;
        tiles[2][width-3] = BlockEntityEnum.GRASS;


        tiles[height-2][1] = BlockEntityEnum.GRASS;
        tiles[height-3][1] = BlockEntityEnum.GRASS;
        tiles[height-3][2] = BlockEntityEnum.GRASS;

        tiles[height-2][width-3] = BlockEntityEnum.GRASS;
        tiles[height-3][width-3] = BlockEntityEnum.GRASS;
        tiles[height-2][width-2] = BlockEntityEnum.GRASS;
        tiles[height-3][width-2] = BlockEntityEnum.GRASS;
        tiles[height-1][width-1] = BlockEntityEnum.GRASS;
    }

    private void placeUnbreakableAndGrass () {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                //Makes frame of unbreakable
                if ((i == 0) || (j == 0) || (i == height - 1) || (j == width - 1) || i % 2 == 0 && j % 2 == 0) {
                    tiles[i][j] = BlockEntityEnum.WALL;
                    //Every-other unbreakable
                } else if (tiles[i][j] != BlockEntityEnum.CRATE) {
                    tiles[i][j] = BlockEntityEnum.GRASS;
                }
            }
        }
    }

    public boolean collisionWithBombs(Player player) {
        boolean playerLeftBomb = true;

        for (Bomb bomb : bombList) {
            if (player != null) {
                playerLeftBomb = bomb.isPlayerLeft();
            }
            assert player != null;
            if(playerLeftBomb && collidingCircles(player, squareToPixel(bomb.getColIndex()), squareToPixel(bomb.getRowIndex()))){
                return true;
            }
        }
        return false;
    }

    public boolean collisionWithBlock(Player player){
        //Maybe create if statements to only check nearby squares
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(getFloorTile(i, j) != BlockEntityEnum.GRASS){
                    boolean isIntersecting = squareCircleInstersect(i, j, player);
                    if (isIntersecting) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean squareHasBomb(int rowIndex, int colIndex){
        for (Bomb b: bombList) {
            if(b.getRowIndex()==rowIndex && b.getColIndex()==colIndex){
                return true;
            }
        }
        return false;
    }

    public void checkIfPlayerLeftBomb(Player player){
        for (Bomb bomb: bombList) {
            if(!bomb.isPlayerLeft()){
                if(!collidingCircles(player, squareToPixel(bomb.getColIndex()), squareToPixel(bomb.getRowIndex()))){
                    bomb.setPlayerLeft(true);
                }
                else{
                }
            }
            else{
                bomb.setPlayerLeft(false);
            }
        }
    }

    public synchronized boolean bombCoordinateCheck(int eRow, int eCol, boolean open, Bomb bomb){
        if(tiles[eRow][eCol] != BlockEntityEnum.GRASS){open = false;}
        if(tiles[eRow][eCol] == BlockEntityEnum.CRATE){
            cratesExploded++;
            Player player = bomb.getPlayer();
            System.out.println("CRATES EXPLODED: "+ cratesExploded);
            player.updateCratesDestroyed();
            System.out.println(player.getName() + " destroyed " + player.getCratesDestroyed());
            tiles[eRow][eCol] = BlockEntityEnum.GRASS;
        }
        if(tiles[eRow][eCol] != BlockEntityEnum.WALL){
            bombExplosionCoords.add(new BombExplosion(eRow, eCol, bomb));}
        return open;
    }

    private boolean collidingCircles(Player player, int x, int y){
        int a = player.getX() - x - UiComponent.getSquareMiddle();
        int b = player.getY() - y - UiComponent.getSquareMiddle();
        int a2 = a * a;
        int b2 = b * b;
        double c = Math.sqrt(a2 + b2);
        return(player.getSize() > c);
    }

    private boolean squareCircleInstersect(int row, int col, Player player) {
        // http://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
        int characterX = player.getX();
        int characterY = player.getY();

        int circleRadius = player.getSize() / 2;
        int squareSize = UiComponent.getSquareSize();
        int squareCenterX = (col*squareSize)+(squareSize/2);
        int squareCenterY = (row*squareSize)+(squareSize/2);

        int circleDistanceX = Math.abs(characterX - squareCenterX);
        int circleDistanceY = Math.abs(characterY - squareCenterY);

        if (circleDistanceX > (squareSize/2 + circleRadius)) { return false; }
        if (circleDistanceY > (squareSize/2 + circleRadius)) { return false; }

        if (circleDistanceX <= (squareSize/2)) { return true; }
        if (circleDistanceY <= (squareSize/2)) { return true; }

        int cornerDistance = (circleDistanceX - squareSize/2)^2 +
                (circleDistanceY - squareSize/2)^2;

        return (cornerDistance <= (circleRadius^2));
    }

    public void setPlayersVulnerable() {
        for(Player player : playersList){
            if(player.isInvincible()){
                player.setTimeInvincible(player.getTimeInvincible()-1);
                if(player.getTimeInvincible() == 0){
                    player.setInvincible(false);
                    player.setTimeInvincible(150);
                }
            }
        }//end for
    }


}