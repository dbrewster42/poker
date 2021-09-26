package com.brewster.poker.model.response;


import com.brewster.poker.bet.BetOptions;

import java.util.List;

public class BetResponse {
    private boolean isBet;
    private List<String> messages;
    private BetOptions betOptions;
    private int userMoney;
    private boolean isDealDone;

    public BetResponse(boolean isBet, List<String> messages, int userMoney, boolean isDealDone) {
        this.isBet = isBet;
        this.messages = messages;
        this.userMoney = userMoney;
        this.isDealDone = isDealDone;
    }

    public BetResponse(BetOptions betOptions, List<String> messages){
        this.betOptions = betOptions;
        this.messages = messages;
    }

    public boolean isBet() {
        return isBet;
    }

    public List<String> getMessages() {
        return messages;
    }

    public BetOptions getBetOptions() {
        return betOptions;
    }

    public int getUserMoney() {
        return userMoney;
    }

    public boolean isDealDone() {
        return isDealDone;
    }
}
