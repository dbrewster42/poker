package com.brewster.poker.bet;

import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.service.BetService;

public class FoldAction extends Bet {
    public FoldAction(Player player, BetRequest betRequest, BetService betManager) {
        super(player, betRequest, betManager);
    }

    @Override
    public String validate() {
        return "";
    }

    @Override
    public String process() {
        betManager.processFold(player);
        return player.getDisplayName() + " has folded";
    }
}
