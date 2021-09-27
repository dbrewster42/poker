package com.brewster.poker.model.response;

import com.brewster.poker.player.Player;

import java.util.List;

public class EndRoundResponse {
     private String message;
     private int pot;
     private Player winner;
     private Player secondWinner;
     private List<Player> activePlayers;

     public EndRoundResponse(int pot, Player winner, List<Player> activePlayers) {
          this.message = winner.getDisplayName() + " has won " + pot + "$ with a " + winner.getPokerHand().getHandName();
          this.pot = pot;
          this.winner = winner;
          this.activePlayers = activePlayers;
     }

     public EndRoundResponse(int pot, Player winner, Player secondWinner, List<Player> activePlayers) {
          this.message = winner.getDisplayName() + " and " + secondWinner.getDisplayName() +
                  " have tied and will split the pot of " + pot + "$ with their poker hand of a " + winner.getPokerHand().getHandName();
          this.pot = pot;
          this.winner = winner;
          this.secondWinner = secondWinner;
          this.activePlayers = activePlayers;
     }

     public EndRoundResponse() {}

     public String getMessage() {
          return message;
     }

     public int getPot() {
          return pot;
     }

     public Player getWinner() {
          return winner;
     }

     public Player getSecondWinner() {
          return secondWinner;
     }

     public List<Player> getActivePlayers() {
          return activePlayers;
     }
}
