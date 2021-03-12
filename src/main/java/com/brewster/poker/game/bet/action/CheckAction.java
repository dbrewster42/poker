package com.brewster.poker.game.bet.action;

import com.brewster.poker.game.Player;
import com.brewster.poker.game.bet.Bet;
import com.brewster.poker.game.bet.BetManager;
import com.brewster.poker.model.request.BetRequest;

public class CheckAction extends Bet {
    public CheckAction(Player player, BetRequest betRequest, BetManager betManager) {
        super(player, betRequest, betManager);
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
