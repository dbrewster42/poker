package com.brewster.poker.bet;

import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.service.BetService;

public class CheckAction extends Bet {
    public CheckAction(Player player, BetRequest betRequest, BetService betManager) {
        super(player, betRequest, betManager);
        this.betAmount = 0;
    }

    @Override
    public String validate() {
        String validationError = "";
        if (betManager.getBetAmount() != 0){
            validationError = "You may only check when there were no previous bets made. You must call or fold";
        }
        return validationError;
    }

    @Override
    public String process() {
        return player.getDisplayName() + " has checked";
    }
}
