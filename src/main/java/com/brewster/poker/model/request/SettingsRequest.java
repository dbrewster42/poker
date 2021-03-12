package com.brewster.poker.model.request;

public class SettingsRequest {
    private String username;
    private String displayName;
    private int numberOfPlayers;
    private boolean customRules;
    private int bigBlind;

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

    public boolean isCustomRules() {
        return customRules;
    }

    public void setCustomRules(boolean customRules) {
        this.customRules = customRules;
    }
}
