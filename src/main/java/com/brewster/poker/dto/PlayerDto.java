package com.brewster.poker.dto;

import com.brewster.poker.game.PokerHand;

public class PlayerDto {
     private String displayName;
     private PokerHand pokerHand;
     private String pokerHandName;

     public PlayerDto(String displayName, PokerHand pokerHand) {
          this.displayName = displayName;
          this.pokerHand = pokerHand;
     }
     public PlayerDto(String displayName, String pokerHandName) {
          this.displayName = displayName;
          this.pokerHandName = pokerHandName;
     }

     public String getDisplayName() {
          return displayName;
     }

     public PokerHand getPokerHand() {
          return pokerHand;
     }

     public String getPokerHandName() {
          return pokerHandName;
     }
}
