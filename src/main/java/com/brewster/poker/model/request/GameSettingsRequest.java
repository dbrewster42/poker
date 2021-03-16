package com.brewster.poker.model.request;

public class GameSettingsRequest {
    private String username;
    private String displayName;
    private boolean customRules;
    private Integer numberOfPlayers;
    private Integer bigBlind;
    private Integer maxBet;
    private boolean fillEmptySlotsWithComputerPlayer;

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

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public Integer getBigBlind() {
        return bigBlind;
    }

    public void setBigBlind(Integer bigBlind) {
        this.bigBlind = bigBlind;
    }

    public boolean isCustomRules() {
        return customRules;
    }

    public void setCustomRules(boolean customRules) {
        this.customRules = customRules;
    }

    public Integer getMaxBet() {
        return maxBet;
    }

    public void setMaxBet(Integer maxBet) {
        this.maxBet = maxBet;
    }
}
