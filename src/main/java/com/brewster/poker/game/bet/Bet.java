package com.brewster.poker.game.bet;

import com.brewster.poker.game.Player;
import com.brewster.poker.model.request.BetRequest;

public abstract class Bet {
    private final int betAmount;
    private final Action chosenAction;
    private final Player player;
    private final BetManager betManager;

//    public Bet(Player player, String action, int betAmount) {
//        this.player = player;
//        this.chosenAction = Action.valueOf(action);
//        this.betAmount = betAmount;
//    }

    public Bet(Player player, BetRequest betRequest, BetManager betManager){
        this.player = player;
        this.betAmount = betRequest.getBetAmount();
        this.chosenAction = Action.valueOf(betRequest.getAction());
        this.betManager = betManager;
    }

    public abstract String validate();

    public abstract String process();

    public Player getPlayer() {
        return player;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public Action getChosenAction() {
        return chosenAction;
    }

    public BetManager getBetManager() {
        return betManager;
    }
}
