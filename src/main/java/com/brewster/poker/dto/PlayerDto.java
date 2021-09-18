package com.brewster.poker.dto;

import com.brewster.poker.card.Card;

import java.util.List;

public class PlayerDto {
     private String displayName;
     private String pokerHandName;
     private List<Card> cards;

     public PlayerDto(String displayName, String pokerHandName) {
          this.displayName = displayName;
          this.pokerHandName = pokerHandName;
     }
     public PlayerDto(String displayName, String pokerHandName, List<Card> cards) {
          this.displayName = displayName;
          this.pokerHandName = pokerHandName;
          this.cards = cards;
     }

     public String getDisplayName() {
          return displayName;
     }

     public String getPokerHandName() {
          return pokerHandName;
     }

     public List<Card> getCards() {
          return cards;
     }
}
