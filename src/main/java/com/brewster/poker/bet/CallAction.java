package com.brewster.poker.bet;

import com.brewster.poker.model.BetManagerEntity;
import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.service.BetService;

public class CallAction extends Bet {

    public CallAction(Player player, int betAmount, BetManagerEntity betManager) {
        super(player, betAmount, betManager);
        this.betAmount = betAmount - player.getCurrentBetAmount();
    }

//    @Override
//    public String validate() {
////        String validationError = "";
////        if (betAmount != betManager.getBetAmount()){
////            validationError = "Error. You have chosen to call the bet, the bet amount should be unchanged";
////        }
////        return validationError;
//        return "";
//    }

    @Override
    public String process() {
        player.betMoney(betAmount);
        betManager.setPot(betManager.getPot() + betAmount);
        return player.getDisplayName() + " has called the bet of " + betAmount + ". The total pot is now at " + betManager.getPot();
    }
}
