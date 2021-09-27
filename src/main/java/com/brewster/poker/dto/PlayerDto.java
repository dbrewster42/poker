package com.brewster.poker.dto;

import com.brewster.poker.card.Card;
import com.brewster.poker.player.Player;

import java.util.List;


public class PlayerDto {
     private String email;
     private String displayName;
     private int money;
     private String pokerHandName;
     private List<Card> cards;

     public PlayerDto() {
     }

     public PlayerDto(String displayName, int money) {
          this.displayName = displayName;
          this.money = money;
     }

     public PlayerDto(String email, String displayName, int money) {
          this.email = email;
          this.displayName = displayName;
          this.money = money;
     }

     public PlayerDto(Player player) {
          this.email = player.getEmail();
          this.displayName = player.getDisplayName();;
          this.money = player.getMoney();
     }

     public String getDisplayName() {
          return displayName;
     }

     public String getUsername() {
          return displayName;
     }

     public int getMoney() {
          return money;
     }

     public String getEmail() {
          return email;
     }

     public void setEmail(String email) {
          this.email = email;
     }

     public void setDisplayName(String displayName) {
          this.displayName = displayName;
     }

     public void setMoney(int money) {
          this.money = money;
     }

     public List<Card> getCards() {
          return cards;
     }

     public void setCards(List<Card> cards) {
          this.cards = cards;
     }

     public String getPokerHandName() {
          return pokerHandName;
     }

     public void setPokerHandName(String pokerHandName) {
          this.pokerHandName = pokerHandName;
     }

     @Override
     public String toString() {
          return "PlayerDto{" + "email='" + email + '\'' + ", displayName='" + displayName + '\'' + ", money=" + money + '}';
     }
}
