package com.brewster.poker.bets;

import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;

public class FoldAction extends Bet {
    public FoldAction(Player player, BetRequest betRequest, BetManager betManager) {
        super(player, betRequest, betManager);
    }

    @Override
    public String validate() {
        return null;
    }

    @Override
    public String process() {
        betManager.setActivePlayers(betManager.getActivePlayers() - 1);
        return player.getDisplayName() + " has folded";
    }
}
