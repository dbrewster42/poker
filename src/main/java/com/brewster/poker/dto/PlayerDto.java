package com.brewster.poker.dto;

import com.brewster.poker.card.Card;
import com.brewster.poker.player.Player;

import java.util.List;

public class PlayerDto {
     private String displayName;
     private String pokerHandName;
     private List<Card> cards;
     private int money;
     private boolean isLastRound;

     public PlayerDto(String displayName, String pokerHandName) {
          this.displayName = displayName;
          this.pokerHandName = pokerHandName;
     }
     public PlayerDto(String displayName, String pokerHandName, List<Card> cards, int money) {
          this.displayName = displayName;
          this.pokerHandName = pokerHandName;
          this.cards = cards;
          this.money = money;
     }
     public PlayerDto(Player player) {
          this.displayName = player.getDisplayName();
          this.cards = player.getCards();
          this.money = player.getMoney();
          this.pokerHandName = player.getPokerHand().getHandName();
     }
     public PlayerDto(Player player, boolean isFullHand){
          this.displayName = player.getDisplayName();
          this.money = player.getMoney();
          this.isLastRound = isFullHand;
          if (isFullHand){
               this.cards = player.getCards().subList(2, 6);
          } else {
               this.cards = player.getCards().subList(2, player.getCards().size());;
          }

     }

     public boolean isLastRound() {
          return isLastRound;
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

     public int getMoney() {
          return money;
     }
}
