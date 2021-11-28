package domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {
    @JsonProperty("playerId")
    public Integer playerId;

    @JsonProperty("lives")
    public Integer lives;

    @JsonProperty("score")
    public float score;

    @JsonProperty("name")
    public String name;
}
