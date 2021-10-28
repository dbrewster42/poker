package com.brewster.poker.model.request;

public class GameSettingsRequest {
    private String username;
    private String displayName;
    private boolean hasJokers;
    private int numberOfPlayers;
    private int bigBlind;
    private int maxBet;
    private boolean fillWithComputerPlayers;
    private int buyIn;
    private int ante;
    private String gameType;

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public void setBigBlind(int bigBlind) {
        this.bigBlind = bigBlind;
    }

    public boolean isHasJokers() {
        return hasJokers;
    }

    public void setHasJokers(boolean hasJokers) {
        this.hasJokers = hasJokers;
    }

    public int getMaxBet() {
        return maxBet;
    }

    public void setMaxBet(int maxBet) {
        this.maxBet = maxBet;
    }

    public boolean isFillWithComputerPlayers() {
        return fillWithComputerPlayers;
    }

    public void setFillWithComputerPlayers(boolean fillWithComputerPlayers) {
        this.fillWithComputerPlayers = fillWithComputerPlayers;
    }

    public int getBuyIn() {
        return buyIn;
    }

    public void setBuyIn(int buyIn) {
        this.buyIn = buyIn;
    }

    public int getAnte() {
        return ante;
    }

    public void setAnte(int ante) {
        this.ante = ante;
    }

    @Override
    public String toString() {
        return "GameSettingsRequest{" +
                "username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                ", wildCards=" + hasJokers +
                ", numberOfPlayers=" + numberOfPlayers +
                ", bigBlind=" + bigBlind +
                ", maxBet=" + maxBet +
                ", fillWithComputerPlayers=" + fillWithComputerPlayers +
                ", gameType=" + gameType +
                '}';
    }
}
