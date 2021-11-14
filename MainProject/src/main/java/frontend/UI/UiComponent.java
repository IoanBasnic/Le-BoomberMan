package frontend.UI;

import frontend.UI.DrawObject.DrawObject;
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
    private final static int SQUARE_MIDDLE = SQUARE_SIZE/2;
    private final GameMapInitializer gameMapInitializer;
    private final AbstractMap<BlockEntityEnum, Color> colorMap;
    private DrawObject drawObject = new DrawObject();

    public UiComponent(GameMapInitializer gameMapInitializer) {
	this.gameMapInitializer = gameMapInitializer;

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
	return new Dimension(this.gameMapInitializer.getWidth() * SQUARE_SIZE, this.gameMapInitializer.getHeight() * SQUARE_SIZE);
    }

    public void UpdateMap() {
	repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;

	for (int rowIndex = 0; rowIndex < gameMapInitializer.getHeight(); rowIndex++) {
	    for (int colIndex = 0; colIndex < gameMapInitializer.getWidth(); colIndex++) {
		g2d.setColor(colorMap.get(this.gameMapInitializer.getFloorTile(rowIndex, colIndex)));
		if(gameMapInitializer.getFloorTile(rowIndex, colIndex)== BlockEntityEnum.CRATE){
			drawObject.drawCrates(rowIndex, colIndex, g2d);
		}
		else if(gameMapInitializer.getFloorTile(rowIndex, colIndex)== BlockEntityEnum.WALL){
			drawObject.drawWall(rowIndex, colIndex, g2d);
		}
		else{
		    drawObject.drawGrass(rowIndex, colIndex, g2d);
		}
	    }
	}

	drawObject.drawPlayer1(gameMapInitializer.getPlayer1(), g2d);
	drawObject.drawPlayer2(gameMapInitializer.getPlayer2(), g2d);
	drawObject.drawPlayer3(gameMapInitializer.getPlayer3(), g2d);
	drawObject.drawPlayer4(gameMapInitializer.getPlayer4(), g2d);

	drawObject.drawBombs(g2d, gameMapInitializer);

	drawObject.drawExplosion(g2d, gameMapInitializer);
    }

}
