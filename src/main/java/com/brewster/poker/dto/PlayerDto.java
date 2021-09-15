package com.brewster.poker.dto;

import com.brewster.poker.card.Card;
import com.brewster.poker.game.PokerHand;

import java.util.List;

public class PlayerDto {
     private String displayName;
     private PokerHand pokerHand;
     private String pokerHandName;
     private List<Card> cards;

     public PlayerDto(String displayName, PokerHand pokerHand) {
          this.displayName = displayName;
          this.pokerHand = pokerHand;
     }
     public PlayerDto(String displayName, String pokerHandName, List<Card> cards) {
          this.displayName = displayName;
          this.pokerHandName = pokerHandName;
          this.cards = cards;
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

     public List<Card> getCards() {
          return cards;
     }
}
