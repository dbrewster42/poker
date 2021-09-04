package com.brewster.poker.model.response;


import java.util.List;

public class BetResponse {
    private boolean isBet;
    private List<String> messages;

    public BetResponse(boolean isBet, List<String> messages) {
        this.isBet = isBet;
        this.messages = messages;
    }

    public boolean isBet() {
        return isBet;
    }

    public List<String> getMessages() {
        return messages;
    }

}
