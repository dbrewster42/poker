package com.brewster.poker.bet;

import com.brewster.poker.model.BetManagerEntity;
import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.service.BetService;

public class FoldAction extends Bet {
    public FoldAction(Player player, int betAmount, BetManagerEntity betManager) {
        super(player, betAmount, betManager);
        this.betAmount = 0;
    }

    @Override
    public String process() {
        betManager.processFold(player);
        return player.getDisplayName() + " has folded";
    }
}
