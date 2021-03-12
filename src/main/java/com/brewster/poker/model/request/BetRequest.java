package com.brewster.poker.model.request;

public class BetRequest {
    private String username;
    private String action;
    private int betAmount;
//    private Action action;
//    private boolean fold;
//    private int riverCardsCount;
//    private Map<String, Integer> bets;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }
}
