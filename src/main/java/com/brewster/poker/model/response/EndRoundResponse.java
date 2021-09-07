package com.brewster.poker.model.response;

import com.brewster.poker.player.Player;

public class EndRoundResponse {
     private String message;
     private int pot;
     private Player winner;

     public EndRoundResponse(String message, int pot, Player winner) {
          this.message = message;
          this.pot = pot;
          this.winner = winner;
     }

     public String getMessage() {
          return message;
     }

     public int getPot() {
          return pot;
     }

     public Player getWinner() {
          return winner;
     }
}
