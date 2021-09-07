package com.brewster.poker.model.response;

import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.player.Player;

import java.util.List;

public class EndRoundResponse {
     private String message;
     private int pot;
     private PlayerDto winner;
     private List<PlayerDto> activePlayers;

     public EndRoundResponse(int pot, PlayerDto winner, List<PlayerDto> activePlayers) {
          this.message = winner.getDisplayName() + " has won " + pot + "$ with a " + winner.getPokerHandName();
          this.pot = pot;
          this.winner = winner;
          this.activePlayers = activePlayers;
     }

     public String getMessage() {
          return message;
     }

     public int getPot() {
          return pot;
     }

     public PlayerDto getWinner() {
          return winner;
     }
}
