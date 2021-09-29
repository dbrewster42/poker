package com.brewster.poker.model.response;

import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.player.Player;

import java.util.List;

public class EndRoundResponse {
     private String message;
     private int pot;
     private PlayerDto winner;
     private PlayerDto secondWinner;
     private List<PlayerDto> activePlayers;

     public EndRoundResponse(int pot, PlayerDto winner, List<PlayerDto> activePlayers) {
          this.message = winner.getDisplayName() + " has won " + pot + "$ with a " + winner.getPokerHandName();
          this.pot = pot;
          this.winner = winner;
          this.activePlayers = activePlayers;
     }

     public EndRoundResponse(int pot, PlayerDto winner, PlayerDto secondWinner, List<PlayerDto> activePlayers) {
          this.message = winner.getDisplayName() + " and " + secondWinner.getDisplayName() +
                  " have tied and will split the pot of " + pot + "$ with their poker hand of a " + winner.getPokerHandName();
          this.pot = pot;
          this.winner = winner;
          this.secondWinner = secondWinner;
          this.activePlayers = activePlayers;
     }

     public EndRoundResponse(String message, List<PlayerDto> activePlayers) {

     }

     public EndRoundResponse() {}

     public String getMessage() {
          return message;
     }

     public int getPot() {
          return pot;
     }

     public PlayerDto getWinner() {
          return winner;
     }

     public PlayerDto getSecondWinner() {
          return secondWinner;
     }

     public List<PlayerDto> getActivePlayers() {
          return activePlayers;
     }
}
