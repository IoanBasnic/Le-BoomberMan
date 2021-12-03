package map_tracker;

import action_and_validation_tracker.ActionTracker;
import frontend.UI.DrawObject.DrawHearts;
import player_and_bomb_tracker.Bomb;
import player_and_bomb_tracker.BombExplosion;
import frontend.UI.UiComponent;
import frontend.game_components.Player;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;


public class GameMapInitializer {
    private final static double CHANCE_FOR_BREAKABLE_BLOCK = 0.5;
    private final BlockEntityEnum[][] tiles;
    private static int PLAYER_X_START = 60;
    private static int PLAYER_Y_START = 60;
    private int width;
    private int height;
    private UiComponent mapListenerInterfaces;
    private Player player1 = null;
    private Player player2 = null;
    private Player player3 = null;
    private Player player4 = null;
    private int index;
    private int playersAlive = 4;

    static ReentrantLock counterLock = new ReentrantLock(true);

    private List<Bomb> bombList= new ArrayList<>();
    private Collection<Bomb> explosionList= new ArrayList<>();
    private Collection<BombExplosion> bombExplosionCoords = new ArrayList<>();
    public boolean isGameOver = false;


    public BlockEntityEnum[][] getTiles() {
        return tiles;
    }

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
       // System.out.println("!!!!!!!!!!!!!!!!!!! " + playersAlive);
        if(playersAlive < 3){
            try {
                for(int i = 0; i < 5; i++){
                    System.out.println("UPDATE");
                    explosionHandler();
                    mapListenerInterfaces.UpdateMap();
                }
                Thread.sleep(2000);
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           // return true;
        }
        return false;
//       return (playersAlive == 2) ? true : false;
       // return isGameOver;
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

        player1.setAlive();
        player2.setAlive();
        player3.setAlive();
        player4.setAlive();

        ActionTracker a = new ActionTracker();
        a.setKeys(uiComponent, this, player1, player2, player3, player4);
    }

    public int squareToPixel(int squareCoord){
        return squareCoord * UiComponent.getSquareSize();
    }

    public void addFloorListener(UiComponent bl) {
        mapListenerInterfaces = bl;
    }

    public UiComponent getUi(){
        return mapListenerInterfaces;
    }

    public void notifyListeners() {
      getUi().UpdateMap();
    }

    /**
     * This method creates a bomb if the given demands are satisfied.
     */
    public void bombCountdown(){
        Collection<Integer> bombIndexesToBeRemoved = new ArrayList<>();
        explosionList.clear();
        index = 0;
        for (Bomb b: bombList) {
            b.setTimeToExplosion(b.getTimeToExplosion() - 1);
            if(b.getTimeToExplosion() == 0){
                b.getPlayer().removeBomb();
                bombIndexesToBeRemoved.add(index);
                explosionList.add(b);
                //Explosion(b);
            }
            index++;
        }
        for (int i: bombIndexesToBeRemoved){bombList.remove(i);}
    }

//    public void Explosion(Bomb e) {
//
//            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
//                @Override
//                protected Void doInBackground() throws Exception {
//                   // System.out.println("Entered explosion " + Thread.currentThread().getName());
//
//                    int eRow = e.getRowIndex();
//                    int eCol = e.getColIndex();
//                    boolean northOpen = true;
//                    boolean southOpen = true;
//                    boolean westOpen = true;
//                    boolean eastOpen = true;
//                    bombExplosionCoords.add(new BombExplosion(eRow, eCol));
//                   // System.out.println("INDEX: " + eRow + "  " + eCol);
//                    for (int i = 1; i < e.getExplosionRadius() + 1; i++) {
//                        if (eRow - i >= 0 && northOpen) {
//                            northOpen = bombCoordinateCheck(eRow - i, eCol, northOpen);
//                        }
//                        if (eRow - i <= height && southOpen) {
//                            southOpen = bombCoordinateCheck(eRow + i, eCol, southOpen);
//                        }
//                        if (eCol - i >= 0 && westOpen) {
//                            westOpen = bombCoordinateCheck(eRow, eCol - i, westOpen);
//                        }
//                        if (eCol + i <= width && eastOpen) {
//                            eastOpen = bombCoordinateCheck(eRow, eCol + i, eastOpen);
//                        }
//                    }
//                    //System.out.println("FINISHED");
//                    return null;
//                }
//            };
//            worker.execute();
//        }

    public void explosionHandler(){
        Collection<BombExplosion> explosionsToBeRemoved = new ArrayList<>();
        for (BombExplosion e: bombExplosionCoords) {
            e.setDuration(e.getDuration()-1);

            if(e.getDuration()==0){
                explosionsToBeRemoved.add(e);
                player1.setHit(false);
                player2.setHit(false);
                player3.setHit(false);
                player4.setHit(false);
            }
        }
        for (BombExplosion e: explosionsToBeRemoved){
            bombExplosionCoords.remove(e);}

        for(Bomb e : explosionList) {
            int eRow = e.getRowIndex();
            int eCol = e.getColIndex();
            boolean northOpen = true;
            boolean southOpen = true;
            boolean westOpen = true;
            boolean eastOpen = true;
            bombExplosionCoords.add(new BombExplosion(eRow, eCol));
            for (int i = 1; i < e.getExplosionRadius() + 1; i++) {
                if (eRow - i >= 0 && northOpen) {
                    northOpen = bombCoordinateCheck(eRow - i, eCol, northOpen);
                }
                if (eRow - i <= height && southOpen) {
                    southOpen = bombCoordinateCheck(eRow + i, eCol, southOpen);
                }
                if (eCol - i >= 0 && westOpen) {
                    westOpen = bombCoordinateCheck(eRow, eCol - i, westOpen);
                }
                if (eCol + i <= width && eastOpen) {
                    eastOpen = bombCoordinateCheck(eRow, eCol + i, eastOpen);
                }
            }
        }
    }

    public void playerInExplosion(){
        for (BombExplosion tup: bombExplosionCoords) {
            if(!player1.hitted() && collidingCircles(player1, squareToPixel(tup.getColIndex()), squareToPixel(tup.getRowIndex()))){
                //System.out.println("In explosion" + " " + player1.getRowIndex() + " " + player1.getColIndex());
                if(!player1.isInvincible()){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    player1.setHit(true);
                    player1.setDead();
                    player1.setInvincible(true);
                    player1.setX(PLAYER_X_START);
                    player1.setY(PLAYER_Y_START);
                    getUi().getDrawHearts().setPlayerHeartsById(0, player1.getNoLifes());
                    if(!player1.IsAlive()){
                        playersAlive--;
                    }
                }
                else{
                    System.out.println("PLAYER IS INVINCIBLE");
                }
            }
            if(collidingCircles(player2, squareToPixel(tup.getColIndex()), squareToPixel(tup.getRowIndex()))){
            }
            if((player3.IsAlive()) && collidingCircles(player3, squareToPixel(tup.getColIndex()), squareToPixel(tup.getRowIndex()))){
                player3.setDead();
                playersAlive--;
            }
            if(collidingCircles(player4, squareToPixel(tup.getColIndex()), squareToPixel(tup.getRowIndex()))){
            }
        }
    }

    public void characterInExplosion(){
        playerInExplosion();
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
                int a = player.getX() - squareToPixel(bomb.getColIndex()) - UiComponent.getSquareMiddle();
                int b = player.getY() - squareToPixel(bomb.getRowIndex()) - UiComponent.getSquareMiddle();
                int a2 = a * a;
                int b2 = b * b;
                double c = Math.sqrt(a2 + b2);
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

    public synchronized boolean bombCoordinateCheck(int eRow, int eCol, boolean open){
        if(tiles[eRow][eCol] != BlockEntityEnum.GRASS){open = false;}
        if(tiles[eRow][eCol] == BlockEntityEnum.CRATE){
            tiles[eRow][eCol] = BlockEntityEnum.GRASS;
        }
        if(tiles[eRow][eCol] != BlockEntityEnum.WALL){
            bombExplosionCoords.add(new BombExplosion(eRow, eCol));}
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

    public void setPlayerVincible() {
        Player player1  = getPlayer1();
        if(player1.isInvincible()){
             player1.setTimeInvincible(player1.getTimeInvincible()-1);
            System.out.println("Time: "+ player1.getTimeInvincible());
             if(player1.getTimeInvincible() == 0){
                 player1.setInvincible(false);
                 player1.setTimeInvincible(150);
                 System.out.println(player1.getName() + " " + player1.isInvincible());
             }
        }
    }
}