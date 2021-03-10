package com.brewster.poker.game.bet;

public class BetOptions {
    private int turn;
    private Action[] possibleActions;
    private int betAmount;

    public BetOptions(int turn, Action[] possibleActions, int betAmount) {
        this.turn = turn;
        this.possibleActions = possibleActions;
        this.betAmount = betAmount;
    }

    public int getTurn() {
        return turn;
    }

    public Action[] getPossibleActions() {
        return possibleActions;
    }

    public int getBetAmount() {
        return betAmount;
    }
}
