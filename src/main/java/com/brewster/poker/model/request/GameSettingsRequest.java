package com.brewster.poker.model.request;

public class GameSettingsRequest {
    private String username;
    private String displayName;
    private boolean useWildCards;
    private int numberOfPlayers;
    private int bigBlind;
    private int maxBet;
    private boolean fillWithComputerPlayers;
    private int buyIn;
    private boolean useAnte;

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

    public boolean isUseWildCards() {
        return useWildCards;
    }

    public void setUseWildCards(boolean useWildCards) {
        this.useWildCards = useWildCards;
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

    public boolean isUseAnte() {
        return useAnte;
    }

    public void setUseAnte(boolean useAnte) {
        this.useAnte = useAnte;
    }

    @Override
    public String toString() {
        return "GameSettingsRequest{" +
                "username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                ", wildCards=" + useWildCards +
                ", numberOfPlayers=" + numberOfPlayers +
                ", bigBlind=" + bigBlind +
                ", maxBet=" + maxBet +
                ", fillWithComputerPlayers=" + fillWithComputerPlayers +
                '}';
    }
}
