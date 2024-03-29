package com.brewster.poker.bet;

import com.brewster.poker.exception.InvalidBetException;
import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.service.BetService;

public class RaiseAction extends Bet {
    public RaiseAction(Player player, BetRequest betRequest, BetService betManager) {
        super(player, betRequest, betManager);
//        this.betAmount = betRequest.getBetAmount();
        this.betAmount = betRequest.getBetAmount() - player.getCurrentBetAmount();
        validate();
    }

    private void validate() {
        if (betAmount < betManager.getBigBlind()){
            throw new InvalidBetException("The minimum bet is " + betManager.getBigBlind() + ". You may not bet less than the blind");
        }
        if (betAmount <= betManager.getBetAmount()){
            throw new InvalidBetException("You selected to Raise the bet. Your bet must be higher than the current bet");
        }
    }

    @Override
    public String process() {
        betManager.setBetAmount(betAmount + player.getCurrentBetAmount());
        player.betMoney(betAmount);
        betManager.setPot(betManager.getPot() + betAmount);
        betManager.resetTurnsLeft();
        return player.getDisplayName() + " has raised the bet to " + betAmount + ". The total pot is now at " + betManager.getPot();
    }
}
