package domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameStartData {
    @JsonProperty("numberOfPlayers")
    public int numberOfPlayers;

    @JsonProperty("livesPerPlayer")
    public int livesPerPlayer;

    @JsonProperty("numberOfBoxes")
    public int numberOfBoxes;

    @JsonProperty("pointsForLife")
    public int pointsForLife;

    @JsonProperty("pointsForBox")
    public int pointsForBox;
}
