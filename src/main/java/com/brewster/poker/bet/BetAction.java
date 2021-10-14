package com.brewster.poker.bet;

import com.brewster.poker.exception.InvalidBetException;
import com.brewster.poker.model.BetManagerEntity;
import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.service.BetService;

public class BetAction extends Bet {

    public BetAction(Player player, BetRequest betRequest, BetManagerEntity betManager) {
        super(player, betRequest, betManager);
//        this.betAmount = betRequest.getBetAmount();
        validate();
    }
    public BetAction(){}

//    public BetAction(Player player, int betAmount, BetManagerEntity betManager) {
//        this.player = player;
//        this.betAmount = betAmount;
//        validate();
//    }

    private void validate() {
        if (betAmount < betManager.getBigBlind()){
            throw new InvalidBetException("The minimum bet is " + betManager.getBigBlind() + ". You may not bet less than the blind");
        }
    }

    @Override
    public String process() {
        player.betMoney(betAmount);
        betManager.setBetAmount(betAmount);
        betManager.setPot(betManager.getPot() + betAmount);
        betManager.resetTurnsLeft();
        return player.getDisplayName() + " has made a bet of " + betAmount + ". The total pot is now at " + betManager.getPot();
    }
}
