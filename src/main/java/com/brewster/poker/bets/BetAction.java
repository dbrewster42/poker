package com.brewster.poker.bets;

import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;

public class BetAction extends Bet {

    public BetAction(Player player, BetRequest betRequest, BetManager betManager) {
        super(player, betRequest, betManager);
    }

    @Override
    public String validate() {
        String validationError = "";
        if (betAmount < betManager.getBigBlind()){
            validationError += "The minimum bet is " + betManager.getBigBlind() + ". You may not bet less than the blind";
        }
        return validationError;
    }

    @Override
    public String process() {
        betManager.setBetAmount(betAmount);
        betManager.setPot(betManager.getPot() + betAmount);
        betManager.resetTurnsLeft();
        return player.getDisplayName() + " has made a bet of " + betAmount + ". The total pot is now at " + betManager.getPot();
    }
}
