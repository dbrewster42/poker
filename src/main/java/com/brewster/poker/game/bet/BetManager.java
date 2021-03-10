package com.brewster.poker.game.bet;

import com.brewster.poker.game.Game;
import com.brewster.poker.game.Player;

public class BetManager {
    private Game game;
    private int bigBlind;
    private int smallBlind;
    private int turn;
    private Action[] checkActions = { Action.BET, Action.CHECK, Action.FOLD };
    private Action[] callActions = { Action.CALL, Action.RAISE, Action.FOLD };
    private int pot;

    public BetManager(Game game, int bigBlind) {
        this.game = game;
        this.bigBlind = bigBlind;
        this.smallBlind = bigBlind / 2;
        this.turn = 0;
    }

    public void makeBet(){
        //TODO 1. validate bet, 2. place bet
    }

    private void adjustTurn(){
        turn++;
        if (turn == game.getNumberOfPlayers()){
            turn = 0;
        }
    }

    public BetOptions getBetOptions(int betAmount){
        Player playerUp = game.getPlayers().get(turn);
        adjustTurn();
        Action[] actionOptions = getPossibleBetActions(betAmount);
        return new BetOptions(playerUp, actionOptions, betAmount);
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
