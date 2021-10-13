package com.brewster.poker.bet;

import com.brewster.poker.model.BetManagerEntity;
import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;

public class FoldAction extends Bet {
    public FoldAction(Player player, BetRequest betRequest, BetManagerEntity betManager) {
        super(player, betRequest, betManager);
        this.betAmount = 0;
    }

    @Override
    public String process() {
        betManager.processFold(player);
        return player.getDisplayName() + " has folded";
    }
}
