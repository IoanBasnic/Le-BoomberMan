package action_and_validation_tracker;

import frontend.game_components.GameObject;
import frontend.game_components.Player;
import map_tracker.GameMapInitializer;
import player_and_bomb_tracker.Bomb;

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
}
