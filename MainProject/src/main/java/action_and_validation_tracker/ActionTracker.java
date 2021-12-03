package action_and_validation_tracker;

import frontend.UI.UiComponent;
import frontend.game_components.GameObject;
import frontend.game_components.Player;
import map_tracker.GameMapInitializer;
import player_and_bomb_tracker.Bomb;

import javax.swing.*;

public class ActionTracker {

    public ActionTracker() {

    }

    public void movePlayer(GameObject.Move move, int deltaX, int deltaY, Player player, GameMapInitializer gameMapInitializer) {
//        System.out.println("movePlayer");
//        System.out.println(deltaX);
//        System.out.println(player);
//        System.out.println("movePlayerFinished");
        player.playerMove(deltaX, deltaY);
        if(gameMapInitializer.collisionWithBlock(player)){
          //  System.out.println("Player " + player.getName() + "  Collision with BLOCK " + move);
            player.playerMoveBack(move, deltaX, deltaY);
        }
        if(gameMapInitializer.collisionWithBombs(player)){
            System.out.println("Player " + player.getName() + "  Collision with BOMB " + move);
            player.playerMoveBack(move, deltaX, deltaY);
            gameMapInitializer.checkIfPlayerLeftBomb(player);
        }
        gameMapInitializer.checkIfPlayerLeftBomb(player);
        //gameMapInitializer.notifyListeners();
    }

    public void dropBomb(int rowIndex, int colIndex, GameMapInitializer floor, Player player) {
//        System.out.println("Bomb dropped!");
//        System.out.println(player.getName());
        if(!floor.squareHasBomb(rowIndex, colIndex)){
            if(player.getBombsPlaced() < 3){
                Bomb b = new Bomb(rowIndex, colIndex, player);
                floor.addToBombList(b, player);
            }
            else{
                System.out.println("RUN out of bombs");
                System.out.println("Player " + player.getName() + " " + player.getBombsPlaced());
            }
        }
        floor.notifyListeners();
    }

    public void setKeys(UiComponent uiComponent, GameMapInitializer floor, Player player1, Player player2, Player player3, Player player4){

        KeyboardAnimation animation = new KeyboardAnimation(uiComponent, player1, 24, floor, this);
        animation.addAction("A", GameObject.Move.LEFT);
        animation.addAction("D", GameObject.Move.RIGHT);
        animation.addAction("W",    GameObject.Move.UP);
        animation.addAction("S",  GameObject.Move.DOWN);
        animation.addBombAction("X", player1);

        KeyboardAnimation animation2 = new KeyboardAnimation(uiComponent, player3, 24, floor, this);
        animation2.addAction("RIGHT", GameObject.Move.RIGHT);
        animation2.addAction("LEFT", GameObject.Move.LEFT);
        animation2.addAction("UP",    GameObject.Move.UP);
        animation2.addAction("DOWN",  GameObject.Move.DOWN);
        animation2.addBombAction("SPACE", player3);


//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke('d'), "moveRight1");
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke('a'), "moveLeft1");
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke('w'), "moveUp1");
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke('s'), "moveDown1");
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke('x'), "dropBomb1");
//        uiComponent.getActionMap().put("moveRight1", player1.right);
//        uiComponent.getActionMap().put("moveLeft1", player1.left);
//        uiComponent.getActionMap().put("moveUp1", player1.up);
//        uiComponent.getActionMap().put("moveDown1", player1.down);
//        uiComponent.getActionMap().put("dropBomb1", player1.dropBomb);
        System.out.println("FINISHED");

//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke('k'), "moveRight2");
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke('h'), "moveLeft2");
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke('u'), "moveUp2");
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke('j'), "moveDown2");
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke('m'), "dropBomb2");
//        uiComponent.getActionMap().put("moveRight2", player2.right);
//        uiComponent.getActionMap().put("moveLeft2", player2.left);
//        uiComponent.getActionMap().put("moveUp2", player2.up);
//        uiComponent.getActionMap().put("moveDown2", player2.down);
//        uiComponent.getActionMap().put("dropBomb2", player2.dropBomb);
//
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight3");
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft3");
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveUp3");
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "moveDown3");
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "dropBomb3");
//        uiComponent.getActionMap().put("moveRight3", player3.right);
//        uiComponent.getActionMap().put("moveLeft3", player3.left);
//        uiComponent.getActionMap().put("moveUp3", player3.up);
//        uiComponent.getActionMap().put("moveDown3", player3.down);
//        uiComponent.getActionMap().put("dropBomb3", player3.dropBomb);
//
//
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke('8'), "moveRight4");
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke('4'), "moveLeft4");
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke('8'), "moveUp4");
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke('5'), "moveDown4");
//        uiComponent.getInputMap().put(KeyStroke.getKeyStroke('2'), "dropBomb4");
//        uiComponent.getActionMap().put("moveRight4", player4.right);
//        uiComponent.getActionMap().put("moveLeft4", player4.left);
//        uiComponent.getActionMap().put("moveUp4", player4.up);
//        uiComponent.getActionMap().put("moveDown4", player4.down);
//        uiComponent.getActionMap().put("dropBomb4", player4.dropBomb);
    }

}
