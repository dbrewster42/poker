package com.brewster.poker.game.bet;


public class Bet {
    private int betAmount;
    private Action chosenAction;

    public Bet(String action, int betAmount) {
        this.chosenAction = Action.valueOf(action);
        this.betAmount = betAmount;
    }

//    private void validateBet(){
//        if
//    }

    public int getBetAmount() {
        return betAmount;
    }

    public Action getChosenAction() {
        return chosenAction;
    }
}
