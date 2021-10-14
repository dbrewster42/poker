package com.brewster.poker.bet;

import com.brewster.poker.exception.InvalidBetException;
import com.brewster.poker.model.BetManagerEntity;
import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;

public class CheckAction extends Bet {
    public CheckAction(Player player, BetRequest betRequest, BetManagerEntity betManager) {
        super(player, betRequest, betManager);
        this.betAmount = 0;
        validate();
    }
    public CheckAction(){}

    private void validate() {
        if (betManager.getBetAmount() != 0){
            throw new InvalidBetException("You may only check when there were no previous bets made. You must call or fold");
        }
    }

    @Override
    public String process() {
        return player.getDisplayName() + " has checked";
    }
}
