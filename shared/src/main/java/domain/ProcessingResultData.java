package domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProcessingResultData {
    @JsonProperty("gameStatus")
    public int gameStatus; //-1 - all players died (no winner), 0 - ongoing, playerId - player with playerId won,

    @JsonProperty("players")
    public List<Player> players;

    @JsonProperty("winner")
    public List<Player> winner;
}
