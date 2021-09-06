package com.brewster.poker.model.response;


import com.brewster.poker.bet.BetOptions;

import java.util.List;

public class BetResponse {
    private boolean isBet;
    private List<String> messages;
    private BetOptions betOptions;

    public BetResponse(boolean isBet, List<String> messages) {
        this.isBet = isBet;
        this.messages = messages;
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
}
