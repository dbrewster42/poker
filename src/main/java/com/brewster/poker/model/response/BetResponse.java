package com.brewster.poker.model.response;

import com.brewster.poker.bets.Bet;
import com.brewster.poker.bets.BetOptions;

import java.util.List;

public class BetResponse {
    private boolean isBet;
    private BetOptions betOptions;
    private List<Bet> bets;
    String message;

    public BetResponse(boolean isBet, BetOptions betOptions, List<Bet> bets) {
        this.isBet = isBet;
        this.betOptions = betOptions;
        this.bets = bets;
    }
    public BetResponse(boolean isBet, String message, List<Bet> bets) {
        this.isBet = isBet;
        this.message = message;
        this.bets = bets;
    }

    public boolean isBet() {
        return isBet;
    }

    public String getMessage(){ return message; }

    public BetOptions getBetOptions() {
        return betOptions;
    }

    public List<Bet> getBets() {
        return bets;
    }
}
//public class BetResponse extends Response {
//    private int minimumBet = 0;
//    private List<Action> possibleActions;
//    private final Map<String, String> headers;
//
//
//    public BetResponse(String body, int statusCode) {
//        super(body, statusCode);
//        this.headers = super.getHeaders();
//    }
//}
