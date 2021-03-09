package com.brewster.poker.model.response;

import com.brewster.poker.game.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BetResponse {
    private int minimumBet;
    private int betAmount;
    private List<Action> possibleActions;
    private final Map<String, String> headers;

    public BetResponse(int betAmount) {
        this.headers = createHeaders();
        this.betAmount = betAmount;
        this.possibleActions = getPossibleBetActions(betAmount);
    }

//    public BetResponse(int betAmount, int smallBlind) {
//        this.headers = createHeaders();
//        this.betAmount = betAmount;
//        this.minimumBet = smallBlind;
//        this.possibleActions = getPossibleBetActions(betAmount);
//    }

    public List<Action> getPossibleBetActions(int betAmount){
        possibleActions = new ArrayList<>();
        possibleActions.add(Action.FOLD);
        if (betAmount > 0){
            possibleActions.add(Action.CALL);
            possibleActions.add(Action.RAISE);
        } else {
            possibleActions.add(Action.BET);
            possibleActions.add(Action.CHECK);
        }
        return possibleActions;
    }

    protected Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Methods", "*");
        headers.put("Access-Control-Allow-Headers", "*");
        return headers;
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
