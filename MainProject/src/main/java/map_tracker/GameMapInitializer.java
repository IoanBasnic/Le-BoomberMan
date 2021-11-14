package map_tracker;

import player_and_bomb_tracker.Bomb;
import player_and_bomb_tracker.BombExplosion;
import frontend.UI.UiComponent;
import frontend.game_components.Player;
import player_and_bomb_tracker.BombTracker;

import java.util.*;


public class GameMapInitializer {
    private final static double CHANCE_FOR_BREAKABLE_BLOCK = 0.5;
    private final BlockEntityEnum[][] tiles;
    private int width;
    private int height;
    private Collection<MapListenerInterface> mapListenerInterfaces = new ArrayList<>();
    private Player player1 = null;
    private Player player2 = null;
    private Player player3 = null;
    private Player player4 = null;
    private BombTracker bombTracker = new BombTracker();
    private List<Bomb> bombList= new ArrayList<>();
    private Collection<Bomb> explosionList= new ArrayList<>();
    private Collection<BombExplosion> bombExplosionCoords = new ArrayList<>();
    private boolean isGameOver = false;

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

    static int PLAYER_X_START = 60;
    static int PLAYER_Y_START = 60;
    public void createPlayer(UiComponent uiComponent, GameMapInitializer floor){
        player1 = new Player(PLAYER_X_START, PLAYER_Y_START, floor);
        player1.setPlayerButtons('d', 'a', 'w', 's', 'x', uiComponent);

        player2 = new Player(PLAYER_X_START * 12+20, PLAYER_Y_START, floor);
        player2.setPlayerButtons('k', 'h', 'u', 'j', 'm', uiComponent);

        player3 = new Player(PLAYER_X_START, PLAYER_Y_START * 12+20, floor);
        player3.setPlayerButtons("RIGHT", "LEFT", "UP", "DOWN", "SPACE", uiComponent);

        player4 = new Player(PLAYER_X_START * 12-20, PLAYER_Y_START * 12+20, floor);
        player4.setPlayerButtons('6', '4', '8', '5', '2', uiComponent);
    }

    public int squareToPixel(int squareCoord){
        return squareCoord * UiComponent.getSquareSize();
    }

    public void addFloorListener(MapListenerInterface bl) {
        mapListenerInterfaces.add(bl);
    }

    public void notifyListeners() {
        for (MapListenerInterface b : mapListenerInterfaces) {
            b.UpdateMap();
        }
    }

    /**
     * This method creates a bomb if the given demands are satisfied.
     */
    public void bombCountdown(){
        Collection<Integer> bombIndexesToBeRemoved = new ArrayList<>();
        explosionList.clear();
        int index = 0;
        for (Bomb b: bombList) {
            b.setTimeToExplosion(b.getTimeToExplosion() - 1);
            if(b.getTimeToExplosion() == 0){
                bombIndexesToBeRemoved.add(index);
                explosionList.add(b);
            }
            index++;
        }
        for (int i: bombIndexesToBeRemoved){bombList.remove(i);}
    }

    public void explosionHandler(){
        Collection<BombExplosion> explosionsToBeRemoved = new ArrayList<>();
        for (BombExplosion e: bombExplosionCoords) {
            e.setDuration(e.getDuration()-1);
            if(e.getDuration()==0){
                explosionsToBeRemoved.add(e);
            }
        }
        for (BombExplosion e: explosionsToBeRemoved){
            bombExplosionCoords.remove(e);}

        for (Bomb e: explosionList) {
            int eRow = e.getRowIndex();
            int eCol = e.getColIndex();
            boolean northOpen = true;
            boolean southOpen = true;
            boolean westOpen = true;
            boolean eastOpen = true;
            bombExplosionCoords.add(new BombExplosion(eRow, eCol));
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
            if (player instanceof Player) {
                playerLeftBomb = bomb.isPlayerLeft();
            }
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


    public void checkIfPlayerLeftBomb(){
        for (Bomb bomb: bombList) {
            if(!bomb.isPlayerLeft()){
                if(!collidingCircles(player1, squareToPixel(bomb.getColIndex()), squareToPixel(bomb.getRowIndex()))){
                    bomb.setPlayerLeft(true);
                }
                if(!collidingCircles(player2, squareToPixel(bomb.getColIndex()), squareToPixel(bomb.getRowIndex()))){
                    bomb.setPlayerLeft(true);
                }
                if(!collidingCircles(player3, squareToPixel(bomb.getColIndex()), squareToPixel(bomb.getRowIndex()))){
                    bomb.setPlayerLeft(true);
                }
                if(!collidingCircles(player4, squareToPixel(bomb.getColIndex()), squareToPixel(bomb.getRowIndex()))){
                    bomb.setPlayerLeft(true);
                }
            }
        }
    }

    private boolean bombCoordinateCheck(int eRow, int eCol, boolean open){
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
        //http://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
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