package player_and_bomb_tracker;

import frontend.game_components.Player;
import map_tracker.BlockEntityEnum;
import map_tracker.GameMapInitializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class BombTracker {

    private List<Bomb> bombList= new ArrayList<>();
    private Collection<BombExplosion> bombExplosionCoords = new ArrayList<>();
    private Collection<Bomb> explosionList= new ArrayList<>();
    private int height = 0;
    private int width = 0;

    public BombTracker() {

    }

    public void bombCountdown(){
        Collection<Integer> bombIndexesToBeRemoved = new ArrayList<>();
        explosionList.clear();
        int index = 0;
        for (Bomb b: bombList) {
            b.setTimeToExplosion(b.getTimeToExplosion() - 1);
            if(b.getTimeToExplosion() == 0){
                bombIndexesToBeRemoved.add(index);
                explosionList.add(b);
            }
            index++;
        }
        for (int i: bombIndexesToBeRemoved){bombList.remove(i);}

    }

}
