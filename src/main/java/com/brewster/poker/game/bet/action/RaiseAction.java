package com.brewster.poker.game.bet.action;

import com.brewster.poker.game.Player;
import com.brewster.poker.game.bet.Bet;
import com.brewster.poker.game.bet.BetManager;
import com.brewster.poker.model.request.BetRequest;

public class RaiseAction extends Bet {
    public RaiseAction(Player player, BetRequest betRequest, BetManager betManager) {
        super(player, betRequest, betManager);
    }

    @Override
    public String validate() {
        String validationError = "";
        if (betAmount < betManager.getBigBlind()){
            validationError += "The minimum bet is " + betManager.getBigBlind() + ". You may not bet less than the blind";
        }
        if (betAmount <= betManager.getBetAmount()){
            validationError = "You selected to Raise the bet. Your bet must be higher than the current bet";
        }
        return validationError;
    }

    @Override
    public String process() {
        betManager.setBetAmount(betAmount);
        betManager.setPot(betManager.getPot() + betAmount);
        betManager.resetTurnsLeft();
        return player.getDisplayName() + " has raised the bet to " + betAmount + ". The total pot is now at " + betManager.getPot();
    }
}
