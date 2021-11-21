package domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InputData {
    @JsonProperty("playerId")
    public String playerId;

    @JsonProperty("boxesDestroyed")
    public int boxesDestroyed;

    @JsonProperty("playerLivesTaken")
    public List<String> playerLivesTaken;
}
