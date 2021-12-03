package domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaPlayer {
    @JsonProperty("playerId")
    public Integer playerId;

    @JsonProperty("lives")
    public Integer lives;

    @JsonProperty("score")
    public int score;

    @JsonProperty("name")
    public String name;
}
