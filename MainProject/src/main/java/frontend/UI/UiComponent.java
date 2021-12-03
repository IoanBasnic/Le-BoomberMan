package frontend.UI;

import frontend.UI.DrawObject.DrawHearts;
import frontend.UI.DrawObject.DrawObject;
import frontend.UI.DrawObject.DrawScoreboard;
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
import java.util.Timer;
import java.util.TimerTask;


public class UiComponent extends JComponent implements Runnable {
	// Constants are static by definition.
	private final static int SQUARE_SIZE = 40;
	private final static int SCOREBOARD_SIZE_AND_HEARTS_BOARD_SIZE = SQUARE_SIZE * 3;
	private final static int SQUARE_MIDDLE = SQUARE_SIZE / 2;
	private final GameMapInitializer gameMapInitializer;
	private final AbstractMap<BlockEntityEnum, Color> colorMap;
	private DrawObject drawObject = new DrawObject();
	private DrawScoreboard drawScoreboard = new DrawScoreboard();
	private DrawHearts drawHearts = new DrawHearts();

	MySeheduler scheduler;
	Timer tmr;

	public UiComponent(GameMapInitializer gameMapInitializer) {
		this.gameMapInitializer = gameMapInitializer;

		colorMap = new EnumMap<>(BlockEntityEnum.class);
		colorMap.put(BlockEntityEnum.GRASS, Color.GREEN);
		colorMap.put(BlockEntityEnum.WALL, Color.BLACK);
		colorMap.put(BlockEntityEnum.CRATE, Color.RED);
	}

	public DrawHearts getDrawHearts() {
		return this.drawHearts;
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
		return new Dimension(this.gameMapInitializer.getWidth() * SQUARE_SIZE + SCOREBOARD_SIZE_AND_HEARTS_BOARD_SIZE, this.gameMapInitializer.getHeight() * SQUARE_SIZE);
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
				if (gameMapInitializer.getFloorTile(rowIndex, colIndex) == BlockEntityEnum.CRATE) {
					drawObject.drawCrates(rowIndex, colIndex, g2d);
				} else if (gameMapInitializer.getFloorTile(rowIndex, colIndex) == BlockEntityEnum.WALL) {
					drawObject.drawWall(rowIndex, colIndex, g2d);
				} else {
					drawObject.drawGrass(rowIndex, colIndex, g2d);
				}
			}
		}

		for (int rowIndex = 0; rowIndex < gameMapInitializer.getHeight(); rowIndex++) {
			for (int colIndex = gameMapInitializer.getWidth(); colIndex < this.gameMapInitializer.getWidth() + SQUARE_SIZE; colIndex++) {
				drawObject.drawWall(rowIndex, colIndex, g2d);
//			g2d.setFont(new Font("Google Sans", Font.PLAIN, 12));
//			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//			g2d.drawString("Texting string", rowIndex, colIndex);
//			g2d.setColor(Color.RED.brighter());
//			g2d.dispose();
			}
		}

//	BlockEntityEnum[][] tiles = gameMapInitializer.getTiles();
		Player player1 = gameMapInitializer.getPlayer1();
		Player player3 = gameMapInitializer.getPlayer3();


		if (!player1.IsAlive()) {
			//System.out.println("PLAYER IS DEAD");
		} else {
			if (player1.isInvincible()) {
				if (player1.getTimeInvincible() % 5 != 0) {
				}else {
					drawObject.drawPlayer1(player1, g2d);
				}
			}
			else{
				drawObject.drawPlayer1(player1, g2d);
			}

		}

//	drawObject.drawPlayer1(gameMapInitializer.getPlayer1(), g2d);
		drawObject.drawPlayer2(gameMapInitializer.getPlayer2(), g2d);

		if (!player3.IsAlive()) {
			//System.out.println("PLAYER IS DEAD");
		} else {
			drawObject.drawPlayer3(player3, g2d);
		}

		//drawObject.drawPlayer3(gameMapInitializer.getPlayer3(), g2d);
		drawObject.drawPlayer4(gameMapInitializer.getPlayer4(), g2d);
		drawObject.drawBombs(g2d, gameMapInitializer);
		drawObject.drawExplosion(g2d, gameMapInitializer);

		drawScoreboard.drawScoreboard(g2d, gameMapInitializer, SQUARE_SIZE);
		drawHearts.drawHearts(g2d, gameMapInitializer, SQUARE_SIZE);
	}

	@Override
	public void run() {
		System.out.println("CALLED");
		System.out.println(Thread.currentThread().getName());
		while (true) {
			this.repaint();
		}
	}

	class MySeheduler extends TimerTask {
		UiComponent cl;

		public MySeheduler(UiComponent c) {
			// TODO Auto-generated constructor stub
			this.cl = c;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			cl.repaint();
		}
	}
}
