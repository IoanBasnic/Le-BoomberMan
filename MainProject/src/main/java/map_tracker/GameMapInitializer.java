package map_tracker;

import action_and_validation_tracker.ActionTracker;
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
    private static int index;

    static ReentrantLock counterLock = new ReentrantLock(true);

    private List<Bomb> bombList= new ArrayList<>();
    private Collection<Bomb> explosionList= new ArrayList<>();
    private Collection<BombExplosion> bombExplosionCoords = new ArrayList<>();
    public boolean isGameOver = false;

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
        return isGameOver;
    }

    public void addToBombList(Bomb bomb) {
        bombList.add(bomb);
    }

    public void createPlayer(UiComponent uiComponent){
        player1 = new Player(PLAYER_X_START, PLAYER_Y_START, this, "Player1");
        player2 = new Player(PLAYER_X_START * 12+20, PLAYER_Y_START, this,"Player2");
        player3 = new Player(PLAYER_X_START, PLAYER_Y_START * 12+20, this,"Player3");
        player4 = new Player(PLAYER_X_START * 12-20, PLAYER_Y_START * 12+20, this,"Player4");

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


//    public void bombCountdown(Bomb b){
//                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
//
//                @Override
//                protected Void doInBackground() throws Exception {
//                    try {
//                        Thread.sleep(2000);
//                        }catch(InterruptedException e){
//                        e.printStackTrace();
//                    }
//                    System.out.println(Thread.currentThread().getName());
//                     System.out.println("Entered bombCountDown");
//                    int time = b.getTimeToExplosion();
//                    int i = 0;
//                    while (i < time) {
//                        b.setTimeToExplosion(b.getTimeToExplosion() - 1);
//                        i++;
//                    }
//                    bombList.remove(b);
//                    explosionList.add(b);
//                    return null;
//                }
//
//            };
//            worker.execute();
//    }


    /**
     * This method creates a bomb if the given demands are satisfied.
     */
    public void bombCountdown(){
        Collection<Integer> bombIndexesToBeRemoved = new ArrayList<>();
        explosionList.clear();
        index = 0;
        for (Bomb b: bombList) {
           // System.out.println("Entered bombCountDown");
            b.setTimeToExplosion(b.getTimeToExplosion() - 1);
           // System.out.println("Timer: " + b.getTimeToExplosion());
            if(b.getTimeToExplosion() == 0){
                bombIndexesToBeRemoved.add(index);
                explosionList.add(b);
            }
            index++;
        }
        for (int i: bombIndexesToBeRemoved){bombList.remove(i);}
    }

//    public static void incrementCounter(){
//        counterLock.lock();
//        // Always good practice to enclose locks in a try-finally block
//        try{
//            System.out.println(Thread.currentThread().getName() + ": " + index);
//            index++;
//        }finally{
//            counterLock.unlock();
//        }
//    }

    public void explosionHandler(){
        Collection<BombExplosion> explosionsToBeRemoved = new ArrayList<>();
        for (BombExplosion e: bombExplosionCoords) {
            System.out.println("Bomb tick: "+ e.getDuration());
            e.setDuration(e.getDuration()-1);

            if(e.getDuration()==0){
                System.out.println("REMOVED");
                explosionsToBeRemoved.add(e);
            }
        }
        for (BombExplosion e: explosionsToBeRemoved){
            bombExplosionCoords.remove(e);}

        for (Bomb e: explosionList) {
            System.out.println("Entered explosion ");

            int eRow = e.getRowIndex();
            int eCol = e.getColIndex();
            boolean northOpen = true;
            boolean southOpen = true;
            boolean westOpen = true;
            boolean eastOpen = true;
            bombExplosionCoords.add(new BombExplosion(eRow, eCol));
            System.out.println("INDEX: " + eRow + "  " + eCol);
            for (int i = 1; i < e.getExplosionRadius()+1; i++) {
                if (eRow - i >= 0 && northOpen) {
                    northOpen = bombCoordinateCheck(eRow-i, eCol, northOpen);
                }
                if (eRow - i <= height && southOpen) {
                    southOpen = bombCoordinateCheck(eRow+i, eCol, southOpen);
                }
                if (eCol - i >= 0 && westOpen) {
                    westOpen = bombCoordinateCheck(eRow, eCol-i, westOpen);
                }
                if (eCol + i <= width && eastOpen) {
                    eastOpen = bombCoordinateCheck(eRow, eCol+i, eastOpen);
                }
            }
        }
    }

    public void playerInExplosion(){
        for (BombExplosion tup: bombExplosionCoords) {
            if(collidingCircles(player1, squareToPixel(tup.getColIndex()), squareToPixel(tup.getRowIndex()))){
                isGameOver = true;
            }
            if(collidingCircles(player2, squareToPixel(tup.getColIndex()), squareToPixel(tup.getRowIndex()))){
                isGameOver = true;
            }
            if(collidingCircles(player3, squareToPixel(tup.getColIndex()), squareToPixel(tup.getRowIndex()))){
                isGameOver = true;
            }
            if(collidingCircles(player4, squareToPixel(tup.getColIndex()), squareToPixel(tup.getRowIndex()))){
                isGameOver = true;
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
               // System.out.println(player.getName() + " " + bomb.isPlayerLeft());
                playerLeftBomb = bomb.isPlayerLeft();
            }
            assert player != null;
            if(playerLeftBomb && collidingCircles(player, squareToPixel(bomb.getColIndex()), squareToPixel(bomb.getRowIndex()))){
                int a = player.getX() - squareToPixel(bomb.getColIndex()) - UiComponent.getSquareMiddle();
                int b = player.getY() - squareToPixel(bomb.getRowIndex()) - UiComponent.getSquareMiddle();
                int a2 = a * a;
                int b2 = b * b;
                double c = Math.sqrt(a2 + b2);
                        System.out.println("A:" + a);
        System.out.println("B:" + b);
        System.out.println("A2:" + a2);
        System.out.println("Bb:" + b2);
        System.out.println("C:"+c);
        System.out.println("Player size: "+ player.getSize());
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
                    System.out.println("LEFT TRUE " + player.getName());
                    bomb.setPlayerLeft(true);
                }
                else{
                    System.out.println("LEFT FALSE " + player.getName());
                }
            }
            else{
                System.out.println("LEFT FALSE " + player.getName());
                bomb.setPlayerLeft(false);
            }
        }
    }

    public boolean bombCoordinateCheck(int eRow, int eCol, boolean open){
        if(tiles[eRow][eCol] != BlockEntityEnum.GRASS){open = false;}
        if(tiles[eRow][eCol] == BlockEntityEnum.CRATE){
            tiles[eRow][eCol] = BlockEntityEnum.GRASS;
        }
        if(tiles[eRow][eCol] != BlockEntityEnum.WALL){
            System.out.println("Yes" + tiles[eRow][eCol]);
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
}