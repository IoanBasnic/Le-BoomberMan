package action_and_validation_tracker;

import frontend.game_components.GameObject;
import frontend.game_components.Player;
import map_tracker.GameMapInitializer;

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
        gameMapInitializer.checkIfPlayerLeftBomb();
        gameMapInitializer.notifyListeners();
    }
}
