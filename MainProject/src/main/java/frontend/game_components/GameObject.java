package frontend.game_components;

import map_tracker.GameMapInitializer;

public class GameObject
{
    private final static int SIZE = 30;
    private int x;
    private int y;
    private int pixelsPerStep;

    protected GameObject(int x, int y, int pixelsPerStep) {
        this.x = x;
        this.y = y;
        this.pixelsPerStep = pixelsPerStep;
    }

    public enum Move
    {
        DOWN(0, 1),
        UP(0, -1),
        RIGHT(1, 0),
        LEFT(-1, 0);

        private final int deltaX;
        private final int deltaY;
        Move(final int deltaX, final int deltaY) {
            System.out.println("X: "+ deltaX + " Y: " + deltaY);
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }
        public int getX(){
            return this.deltaX;
        }
        public int getY(){
            return this.deltaY;
        }
    }

    public void move(int deltaX, int deltaY) {
        y += deltaY * pixelsPerStep;
        x += deltaX * pixelsPerStep;
    }

    public void moveBack(Move currentDirection, int deltaX, int deltaY, GameMapInitializer floor) {
        if (currentDirection == Move.DOWN) {
           // System.out.println(deltaX + "  " + deltaY);
            if(deltaX == -1){
                move(1,0);
            }
            if(deltaX == 1){
                move(-1,0);
            }
            move(0, -1);
        } else if (currentDirection == Move.UP) {
            move(0,1);
        } else if (currentDirection == Move.LEFT) {
            //System.out.println(deltaX + "  " + deltaY);
            if(deltaY == 1){
                move(0, -1);
            }
            if(deltaY == -1){
                move(0,1);
            }
            move(1, 0);
        } else if (currentDirection == Move.RIGHT) {
           // System.out.println(deltaX + "  " + deltaY);
            if(deltaY == 1){
                move(0, -1);
            }
            if(deltaY == -1){
                move(0,1);
            }
            move(-1 , 0);
        }
    }

    public int getSize() {
        return SIZE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColIndex() {
        return GameMapInitializer.pixelToSquare(x);
    }

    public int getRowIndex() {
        return GameMapInitializer.pixelToSquare(y);
    }
}
