package com.brewster.poker.model.response;

import java.util.Map;

public class PlayersResponse {
    Map<String, Integer> players;

    public Map<String, Integer> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, Integer> players) {
        this.players = players;
    }
}
