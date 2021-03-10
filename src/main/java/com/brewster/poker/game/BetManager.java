package com.brewster.poker.game;

import java.util.ArrayList;
import java.util.List;

public class BetManager {
    private Game game;
    private List<Player> players;
    private int bigBlind;
    private int smallBlind;
    private int turn;
    private int minimumBet;
    private int betAmount;
    private Object[] checkActions = { Action.BET, Action.CHECK, Action.FOLD, bigBlind };
    private Object[] callActions = { Action.CALL, Action.RAISE, Action.FOLD, bigBlind };
    //TODO bet container?

    public BetManager(Game game, int bigBlind) {
        this.game = game;
        this.players = game.getPlayers();
        this.bigBlind = bigBlind;
        this.smallBlind = bigBlind / 2;
        this.turn = 0;
    }

    public Object[] getPossibleBetActions(int betAmount){
        if (betAmount > 0){
            callActions[3] = betAmount;
            return callActions;
        } else {
            return checkActions;
        }
    }
//    public List<Action> getPossibleBetActions(int betAmount){
//        possibleActions = new ArrayList<>();
//        possibleActions.add(Action.FOLD);
//        if (betAmount > 0){
//            possibleActions.add(Action.CALL);
//            possibleActions.add(Action.RAISE);
//        } else {
//            possibleActions.add(Action.BET);
//            possibleActions.add(Action.CHECK);
//        }
//        return possibleActions;
//    }


}
