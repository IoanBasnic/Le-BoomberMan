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
        player.playerMove(deltaX, deltaY);
        if(gameMapInitializer.collisionWithBlock(player)){
            player.playerMoveBack(move, deltaX, deltaY);
        }
        if(gameMapInitializer.collisionWithBombs(player)){
            System.out.println("Player " + player.getName() + "  Collision with BOMB " + move);
            player.playerMoveBack(move, deltaX, deltaY);
            gameMapInitializer.checkIfPlayerLeftBomb(player);
        }
        gameMapInitializer.checkIfPlayerLeftBomb(player);
    }

    public void dropBomb(int rowIndex, int colIndex, GameMapInitializer floor, Player player) {
        if(!floor.squareHasBomb(rowIndex, colIndex)){
            if(player.getBombsPlaced() < 3){
                Bomb b = new Bomb(rowIndex, colIndex, player);

                //TODO: clean up one flow
                floor.createBombThread(b, player);
//                floor.addToBombList(b, player);
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

        KeyboardAnimation animation3 = new KeyboardAnimation(uiComponent, player2, 24, floor, this);
        animation3.addAction("K", GameObject.Move.RIGHT);
        animation3.addAction("H", GameObject.Move.LEFT);
        animation3.addAction("U",    GameObject.Move.UP);
        animation3.addAction("J",  GameObject.Move.DOWN);
        animation3.addBombAction("M", player2);

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
