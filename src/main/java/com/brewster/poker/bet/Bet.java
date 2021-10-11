package com.brewster.poker.bet;

import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.BetManagerEntity;

public abstract class Bet {
    protected int betAmount = 0;
    protected final Player player;
//    protected final Action chosenAction;
    //TODO add message
    protected final BetManagerEntity betManager;
    protected String message;

    public Bet(Player player, int betAmount, BetManagerEntity betManager){
        this.player = player;
//        this.chosenAction = Action.valueOf(betRequest.getAction());
//        this.betAmount = betRequest.getBetAmount();
        this.betAmount = betAmount;
        this.betManager = betManager;
    }

    public abstract String process();

    public Player getPlayer() {
        return player;
    }

    public int getBetAmount() {
        return betAmount;
    }

//    public Action getChosenAction() {
//        return chosenAction;
//    }

    public BetManagerEntity getBetManager() {
        return betManager;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
