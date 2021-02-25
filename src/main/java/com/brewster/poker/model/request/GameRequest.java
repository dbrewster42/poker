package com.brewster.poker.model.request;

public class GameRequest {
    private int players;
    private int blinds;
    private int maxBet;

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getBlinds() {
        return blinds;
    }

    public void setBlinds(int blinds) {
        this.blinds = blinds;
    }

    public int getMaxBet() {
        return maxBet;
    }

    public void setMaxBet(int maxBet) {
        this.maxBet = maxBet;
    }
}
