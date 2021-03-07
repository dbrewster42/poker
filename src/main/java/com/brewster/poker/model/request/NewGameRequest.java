package com.brewster.poker.model.request;

public class NewGameRequest {
    private int players;
    private int bigBlind;
    private int maxBet;

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public void setBigBlind(int blind) {
        this.bigBlind = blind;
    }

    public int getMaxBet() {
        return maxBet;
    }

    public void setMaxBet(int maxBet) {
        this.maxBet = maxBet;
    }
}
