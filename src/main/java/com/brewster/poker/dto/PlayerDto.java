package com.brewster.poker.dto;

import com.brewster.poker.card.Card;
import com.brewster.poker.card.PokerHandEnum;
import com.brewster.poker.player.Player;

import java.util.List;
import java.util.Optional;

public class PlayerDto extends Dto {
     private String displayName;
     private String pokerHandName;
     private List<Card> cards;
     private int money;

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
          this.pokerHandName = Optional.ofNullable(player.getPokerHand()).map(PokerHandEnum::getHandName).orElse("");
//          this.pokerHandName = player.getPokerHand().getHandName();
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

     public void setCards(List<Card> cards) {
          this.cards = cards;
     }

     public int getMoney() {
          return money;
     }
}
