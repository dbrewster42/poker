package com.brewster.poker.model.request;


import com.brewster.poker.game.bet.Action;

public class BetRequest {
    private String username;
    private Action action;
    private int betAmount;
//    private boolean fold;
//    private int riverCardsCount;
//    private Map<String, Integer> bets;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }
}
