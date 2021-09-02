package com.brewster.poker.model.response;

import com.brewster.poker.bets.Bet;
import com.brewster.poker.bets.BetOptions;
import com.brewster.poker.dto.BetDto;

import java.util.List;

public class BetResponse {
    private boolean isBet;
//    private List<Bet> bets;
    private List<BetDto> bets;
    private String message;
//    private BetOptions betOptions;
//
//    public BetResponse(boolean isBet, BetOptions betOptions, List<BetDto> bets) {
//        this.isBet = isBet;
//        this.betOptions = betOptions;
//        this.bets = bets;
//    }
    public BetResponse(boolean isBet, String message, List<BetDto> bets) {
        this.isBet = isBet;
        this.message = message;
        this.bets = bets;
    }

    public boolean isBet() {
        return isBet;
    }

    public String getMessage(){ return message; }

//    public BetOptions getBetOptions() {
//        return betOptions;
//    }

    public List<BetDto> getBets() {
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
