package com.brewster.poker.model;

import com.brewster.poker.bet.Bet;

public class BetEntity {
     private String userEmail;
     protected String chosenAction;
     private int betAmount;
//     private String betMessage;

     public BetEntity(Bet bet) {
          this.betAmount = bet.getBetAmount();
          this.chosenAction = bet.getChosenAction();
          this.userEmail = bet.getPlayer().getEmail();
     }
     public BetEntity(){}

     public String getUserEmail() {
          return userEmail;
     }

     public void setUserEmail(String userEmail) {
          this.userEmail = userEmail;
     }

     public String getChosenAction() {
          return chosenAction;
     }

     public void setChosenAction(String chosenAction) {
          this.chosenAction = chosenAction;
     }

     public int getBetAmount() {
          return betAmount;
     }

     public void setBetAmount(int betAmount) {
          this.betAmount = betAmount;
     }
}
