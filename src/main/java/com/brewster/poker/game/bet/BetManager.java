package com.brewster.poker.game.bet;

import com.brewster.poker.game.Game;
import com.brewster.poker.game.Player;

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
    private List<Action> possibleActions;
    private Action[] checkActions = { Action.BET, Action.CHECK, Action.FOLD };
    private Action[] callActions = { Action.CALL, Action.RAISE, Action.FOLD };
    //TODO bet container?

    public BetManager(Game game, int bigBlind) {
        this.game = game;
        this.players = game.getPlayers();
        this.bigBlind = bigBlind;
        this.smallBlind = bigBlind / 2;
        this.turn = 0;
        this.possibleActions = new ArrayList<>();
    }

    public BetOptions getBetOptions(int betAmount){
        Action[] actionOptions = getPossibleBetActions(0);
        return new BetOptions(turn, actionOptions, betAmount);
    }

    public Action[] getPossibleBetActions(int betAmount){
        if (betAmount > 0){
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
