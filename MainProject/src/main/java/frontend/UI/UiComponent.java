package frontend.UI;

import player_and_bomb_tracker.Bomb;
import player_and_bomb_tracker.BombExplosion;
import frontend.game_components.Player;
import map_tracker.MapListenerInterface;
import map_tracker.BlockEntityEnum;
import map_tracker.GameMapInitializer;

import javax.swing.*;
import java.awt.*;
import java.util.AbstractMap;
import java.util.EnumMap;


public class UiComponent extends JComponent implements MapListenerInterface
{
    // Constants are static by definition.
    private final static int SQUARE_SIZE = 40;
    private final static int CHARACTER_ADJUSTMENT_FOR_PAINT = 15;
    private final static int SQUARE_MIDDLE = SQUARE_SIZE/2;
    private final static int BOMB_ADJUSTMENT_1 =5;
    private final static int BOMB_ADJUSTMENT_2 =10;
    // Defining painting parameters
    private final static int PAINT_PARAMETER_13 = 13;
    private final static int PAINT_PARAMETER_15 = 15;
    private final static int PAINT_PARAMETER_17 = 17;
    private final static int PAINT_PARAMETER_18 = 18;
    private final static int PAINT_PARAMETER_19 = 19;
    private final static int PAINT_PARAMETER_20 = 20;
    private final static int PAINT_PARAMETER_24 = 24;
    private final GameMapInitializer floor;
    private final AbstractMap<BlockEntityEnum, Color> colorMap;

    public UiComponent(GameMapInitializer floor) {
	this.floor = floor;

	colorMap = new EnumMap<>(BlockEntityEnum.class);
	colorMap.put(BlockEntityEnum.GRASS, Color.GREEN);
	colorMap.put(BlockEntityEnum.WALL, Color.BLACK);
	colorMap.put(BlockEntityEnum.CRATE, Color.RED);
    }

    // This method is static since each square has the same size.
    public static int getSquareSize() {
	return SQUARE_SIZE;
    }

    // This method is static since each square has the same size.
    public static int getSquareMiddle() {
	return SQUARE_MIDDLE;
    }

    public Dimension getPreferredSize() {
	super.getPreferredSize();
	return new Dimension(this.floor.getWidth() * SQUARE_SIZE, this.floor.getHeight() * SQUARE_SIZE);
    }

    public void UpdateMap() {
	repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	for (int rowIndex = 0; rowIndex < floor.getHeight(); rowIndex++) {
	    for (int colIndex = 0; colIndex < floor.getWidth(); colIndex++) {
		g2d.setColor(colorMap.get(this.floor.getFloorTile(rowIndex, colIndex)));
		if(floor.getFloorTile(rowIndex, colIndex)== BlockEntityEnum.CRATE){
		    paintCrates(rowIndex, colIndex, g2d);
		}
		else if(floor.getFloorTile(rowIndex, colIndex)== BlockEntityEnum.WALL){
		    paintWall(rowIndex, colIndex, g2d);
		}
		else{
		    paintGrass(rowIndex, colIndex, g2d);
		}
	    }
	}
	// Paint player:
	paintPlayer(floor.getPlayer1(), g2d);
	paintPlayer(floor.getPlayer2(), g2d);
	paintPlayer(floor.getPlayer3(), g2d);
	paintPlayer(floor.getPlayer4(), g2d);
	//Paint bombs
	for (Bomb b: floor.getBombList()) {
	    g2d.setColor(Color.RED);
	    int bombX = floor.squareToPixel(b.getColIndex());
	    int bombY = floor.squareToPixel(b.getRowIndex());
	    g2d.fillOval(bombX + BOMB_ADJUSTMENT_1, bombY + BOMB_ADJUSTMENT_1, Bomb.getBOMBSIZE(), Bomb.getBOMBSIZE());
	    g2d.setColor(Color.ORANGE);
	    g2d.fillOval(bombX + BOMB_ADJUSTMENT_2, bombY + BOMB_ADJUSTMENT_1, BOMB_ADJUSTMENT_1, BOMB_ADJUSTMENT_2);
	}

	//Paint explosions
	g2d.setColor(Color.ORANGE);
	for (BombExplosion tup: floor.getBombExplosionCoords()) {
	    g2d.fillOval(floor.squareToPixel(tup.getColIndex()) + BOMB_ADJUSTMENT_1, floor.squareToPixel(tup.getRowIndex()) +
										     BOMB_ADJUSTMENT_1, Bomb.getBOMBSIZE(), Bomb.getBOMBSIZE());
	}
    }

    private void paintCrates(int rowIndex, int colIndex, Graphics g2d){
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

    private void paintWall(int rowIndex, int colIndex, Graphics g2d){
	g2d.fillRect(colIndex * SQUARE_SIZE, rowIndex * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
	g2d.setColor(Color.DARK_GRAY);
	g2d.drawLine(colIndex* SQUARE_SIZE, rowIndex*SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE);
	g2d.drawLine(colIndex* SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
	g2d.drawLine(colIndex* SQUARE_SIZE, rowIndex*SQUARE_SIZE, colIndex*SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
	g2d.drawLine(colIndex* SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
    }

    private void paintGrass(int rowIndex, int colIndex, Graphics g2d){
	g2d.setColor(new Color(6, 87, 26));
	g2d.fillRect(colIndex * SQUARE_SIZE, rowIndex * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
	g2d.setColor(new Color(26, 255, 74));
	g2d.drawLine(colIndex* SQUARE_SIZE+5, rowIndex*SQUARE_SIZE+10, colIndex * SQUARE_SIZE + 10, rowIndex * SQUARE_SIZE + 5);
	g2d.drawLine(colIndex* SQUARE_SIZE+5, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE, colIndex * SQUARE_SIZE + SQUARE_MIDDLE, rowIndex * SQUARE_SIZE + 5);
	g2d.drawLine(colIndex* SQUARE_SIZE+5, rowIndex*SQUARE_SIZE+SQUARE_MIDDLE+10, colIndex * SQUARE_SIZE + SQUARE_MIDDLE + 10, rowIndex * SQUARE_SIZE + 5);
    }

    private void paintPlayer(Player player, Graphics g2d){
	// Paint hat
	g2d.setColor(Color.BLUE);
	g2d.fillOval(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_15, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT-2, PAINT_PARAMETER_15, PAINT_PARAMETER_15);
	// Paint body
	g2d.setColor(Color.LIGHT_GRAY);
	g2d.fillOval(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getSize(), player.getSize());
	// Paint face
	g2d.setColor(Color.PINK);
	g2d.fillOval(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+3, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+3, player.getSize()-6, player.getSize()-6);
	// Paint eyes
	g2d.setColor(Color.BLACK);
	g2d.drawLine(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_18);
	g2d.drawLine(player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_18);
    }
}
