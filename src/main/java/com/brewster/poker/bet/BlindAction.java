package com.brewster.poker.bet;

import com.brewster.poker.model.BetManagerEntity;
import com.brewster.poker.player.Player;

public class BlindAction extends Bet {

     public BlindAction(Player player, int betAmount, String chosenAction, BetManagerEntity betManager) {
          super(player, betAmount, chosenAction, betManager);
     }
     public BlindAction(){}

     @Override
     public String process() {
          player.betMoney(betAmount);
          betManager.setBetAmount(betAmount);
          betManager.setPot(betManager.getPot() + betAmount);
//          betManager.resetTurnsLeft();
          return player.getDisplayName() + " posts the $" + betAmount + " blind";
     }
}
