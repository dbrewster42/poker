package com.brewster.poker.game.bet;

import com.brewster.poker.game.HumanPlayer;
import com.brewster.poker.model.request.BetRequest;

public abstract class Bet {
    protected final int betAmount;
    protected final HumanPlayer player;
    protected final BetManager betManager;
    protected final Action chosenAction;

    public Bet(HumanPlayer player, BetRequest betRequest, BetManager betManager){
        this.player = player;
        this.betAmount = betRequest.getBetAmount();
        this.chosenAction = Action.valueOf(betRequest.getAction());
        this.betManager = betManager;
    }

    public abstract String validate();

    public abstract String process();

    public HumanPlayer getPlayer() {
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
