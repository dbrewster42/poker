package com.brewster.poker.bet;

import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.service.BetService;

public abstract class Bet {
    protected int betAmount = 0;
    protected final Player player;
    protected final BetService betManager;
    protected final Action chosenAction;

    public Bet(Player player, BetRequest betRequest, BetService betManager){
        this.player = player;
        this.chosenAction = Action.valueOf(betRequest.getAction());
        this.betManager = betManager;
    }

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

    public BetService getBetManager() {
        return betManager;
    }
}
