package com.brewster.poker.bet;

import com.brewster.poker.model.BetManagerEntity;
import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;

public class CallAction extends Bet {

    public CallAction(Player player, BetRequest betRequest, BetManagerEntity betManager) {
        super(player, betRequest, betManager);
        this.betAmount = betAmount - player.getCurrentBetAmount();
    }
    public CallAction(){}

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
