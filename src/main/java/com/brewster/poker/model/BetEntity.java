package com.brewster.poker.model;


import org.springframework.data.annotation.Id;

public class BetEntity {
     @Id
     private long id;
     private int betAmount;
     private String betMessage;

     public BetEntity(int betAmount, String betMessage) {
          this.betAmount = betAmount;
          this.betMessage = betMessage;
     }

     public long getId() {
          return id;
     }

     public int getBetAmount() {
          return betAmount;
     }

     public String getBetMessage() {
          return betMessage;
     }
}
