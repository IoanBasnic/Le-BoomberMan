package frontend.UI.DrawObject;

import frontend.game_components.Player;
import map_tracker.GameMapInitializer;
import player_and_bomb_tracker.Bomb;
import player_and_bomb_tracker.BombExplosion;

import java.awt.*;

public class DrawObject {
    // Constants are static by definition.
    private final static int SQUARE_SIZE = 40;
    private final static int CHARACTER_ADJUSTMENT_FOR_PAINT = 15;
    private final static int SQUARE_MIDDLE = SQUARE_SIZE/2;
    // Defining painting parameters
    private final static int PAINT_PARAMETER_18 = 18;
    private final static int PAINT_PARAMETER_20 = 20;
    private final static int BOMB_ADJUSTMENT_1 =5;
    private final static int BOMB_ADJUSTMENT_2 =10;

    public void drawBombs(Graphics g2d, GameMapInitializer gameMapInitializer) {
        for (Bomb b: gameMapInitializer.getBombList()) {
            g2d.setColor(Color.RED);
            int bombX = gameMapInitializer.squareToPixel(b.getColIndex());
            int bombY = gameMapInitializer.squareToPixel(b.getRowIndex());
            g2d.fillOval(bombX + BOMB_ADJUSTMENT_1, bombY + BOMB_ADJUSTMENT_1, Bomb.getBOMBSIZE(), Bomb.getBOMBSIZE());
            g2d.setColor(Color.ORANGE);
            g2d.fillOval(bombX + BOMB_ADJUSTMENT_2, bombY + BOMB_ADJUSTMENT_1, BOMB_ADJUSTMENT_1, BOMB_ADJUSTMENT_2);
        }
    }

    public void drawExplosion(Graphics g2d, GameMapInitializer gameMapInitializer) {
        g2d.setColor(Color.ORANGE);
        for (BombExplosion tup: gameMapInitializer.getBombExplosionCoords()) {
            g2d.fillOval(gameMapInitializer.squareToPixel(tup.getColIndex()) + BOMB_ADJUSTMENT_1, gameMapInitializer.squareToPixel(tup.getRowIndex()) +
                    BOMB_ADJUSTMENT_1, Bomb.getBOMBSIZE(), Bomb.getBOMBSIZE());
        }
    }

    public void drawCrates(int rowIndex, int colIndex, Graphics g2d){
        g2d.setColor(new Color(142, 109, 33));
        g2d.fillRect(colIndex * SQUARE_SIZE, rowIndex * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        g2d.setColor(new Color(74, 53, 25));
        g2d.drawLine(colIndex* SQUARE_SIZE+1, rowIndex*SQUARE_SIZE+5, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE+5);

        g2d.drawLine(colIndex* SQUARE_SIZE+1, rowIndex*SQUARE_SIZE+SQUARE_SIZE, colIndex*SQUARE_SIZE+1, rowIndex*SQUARE_SIZE+10);
        g2d.drawLine(colIndex* SQUARE_SIZE+SQUARE_MIDDLE+15, rowIndex*SQUARE_SIZE+SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_MIDDLE+15, rowIndex*SQUARE_SIZE+10);

        g2d.drawLine(colIndex* SQUARE_SIZE+1, rowIndex*SQUARE_SIZE+SQUARE_SIZE - 2, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE + SQUARE_SIZE - 2);

        g2d.drawLine(colIndex* SQUARE_SIZE+5, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE+15, colIndex * SQUARE_SIZE + SQUARE_MIDDLE + 15, rowIndex * SQUARE_SIZE + 5);
        g2d.drawLine(colIndex* SQUARE_SIZE+5, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE+10, colIndex * SQUARE_SIZE + SQUARE_MIDDLE + 10, rowIndex * SQUARE_SIZE + 5);
    }

    public void drawWall(int rowIndex, int colIndex, Graphics g2d){
        g2d.fillRect(colIndex * SQUARE_SIZE, rowIndex * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawLine(colIndex* SQUARE_SIZE, rowIndex*SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE);
        g2d.drawLine(colIndex* SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
        g2d.drawLine(colIndex* SQUARE_SIZE, rowIndex*SQUARE_SIZE, colIndex*SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
        g2d.drawLine(colIndex* SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
    }

    public void drawGrass(int rowIndex, int colIndex, Graphics g2d){
        g2d.setColor(new Color(6, 87, 26));
        g2d.fillRect(colIndex * SQUARE_SIZE, rowIndex * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        g2d.setColor(new Color(26, 255, 74));
        g2d.drawLine(colIndex* SQUARE_SIZE+5, rowIndex*SQUARE_SIZE+10, colIndex * SQUARE_SIZE + 10, rowIndex * SQUARE_SIZE + 5);
        g2d.drawLine(colIndex* SQUARE_SIZE+5, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE, colIndex * SQUARE_SIZE + SQUARE_MIDDLE, rowIndex * SQUARE_SIZE + 5);
        g2d.drawLine(colIndex* SQUARE_SIZE+5, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE+10, colIndex * SQUARE_SIZE + SQUARE_MIDDLE + 10, rowIndex * SQUARE_SIZE + 5);
    }

    public void drawPlayer1(Player player, Graphics g2d){
        // Paint body
        g2d.setColor(Color.RED);
        g2d.fillOval(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getSize(), player.getSize());
        // Paint eyes
        g2d.setColor(Color.BLACK);
        g2d.drawLine(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+18);
        g2d.drawLine(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+18);
    }

    public void drawPlayer2(Player player, Graphics g2d){
        // Paint body
        g2d.setColor(Color.BLUE);
        g2d.fillOval(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getSize(), player.getSize());
        // Paint eyes
        g2d.setColor(Color.BLACK);
        g2d.drawLine(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_18);
        g2d.drawLine(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_18);
    }

    public void drawPlayer3(Player player, Graphics g2d){
        // Paint body
        g2d.setColor(Color.ORANGE);
        g2d.fillOval(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getSize(), player.getSize());
        // Paint eyes
        g2d.setColor(Color.BLACK);
        g2d.drawLine(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_18);
        g2d.drawLine(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_18);
    }

    public void drawPlayer4(Player player, Graphics g2d){
        // Paint body
        g2d.setColor(Color.MAGENTA);
        g2d.fillOval(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getSize(), player.getSize());
        // Paint eyes
        g2d.setColor(Color.BLACK);
        g2d.drawLine(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_18);
        g2d.drawLine(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_18);
    }
}
