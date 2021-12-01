package domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BombExplosionData {
    @JsonProperty("playerId")
    public Integer playerId;

    @JsonProperty("boxesDestroyed")
    public int boxesDestroyed;

    @JsonProperty("playerLivesTaken")
    public List<Integer> playerLivesTaken;
}
