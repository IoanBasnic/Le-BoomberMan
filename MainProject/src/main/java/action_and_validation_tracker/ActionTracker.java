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

    public void movePlayer(GameObject.Move move, Player player, GameMapInitializer gameMapInitializer) {
        System.out.println(move);
        System.out.println(player);
        player.playerMove(move);
        if(gameMapInitializer.collisionWithBlock(player)){
            player.playerMoveBack(move);
        }
        if(gameMapInitializer.collisionWithBombs(player)){
            player.playerMoveBack(move);
        }
        gameMapInitializer.checkIfPlayerLeftBomb(player);
        gameMapInitializer.notifyListeners();
    }

    public void dropBomb(int rowIndex, int colIndex, GameMapInitializer floor) {
        if(!floor.squareHasBomb(rowIndex, colIndex) && floor.getBombListSize() < 1){
            floor.addToBombList(new Bomb(rowIndex, colIndex));
        }
        floor.notifyListeners();
    }

    public void setKeys(char r, char l, char u, char d, char placeBomb, UiComponent uiComponent, Player player){
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(r), "moveRight");
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(l), "moveLeft");
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(u), "moveUp");
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(d), "moveDown");
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(placeBomb), "dropBomb");
        uiComponent.getActionMap().put("moveRight", player.right);
        uiComponent.getActionMap().put("moveLeft", player.left);
        uiComponent.getActionMap().put("moveUp", player.up);
        uiComponent.getActionMap().put("moveDown", player.down);
        uiComponent.getActionMap().put("dropBomb", player.dropBomb);
    }

    public void setKeys(String r, String l, String u, String d, String placeBomb, UiComponent uiComponent, Player player){
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(r), "moveRight");
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(l), "moveLeft");
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(u), "moveUp");
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(d), "moveDown");
        uiComponent.getInputMap().put(KeyStroke.getKeyStroke(placeBomb), "dropBomb");
        uiComponent.getActionMap().put("moveRight", player.right);
        uiComponent.getActionMap().put("moveLeft", player.left);
        uiComponent.getActionMap().put("moveUp", player.up);
        uiComponent.getActionMap().put("moveDown", player.down);
        uiComponent.getActionMap().put("dropBomb", player.dropBomb);
    }
}
