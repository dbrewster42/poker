package com.brewster.poker.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Bet {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private long id;
     private int betAmount;
     @ManyToOne
     private User player;
     private String betMessage;

     public Bet(int betAmount, User player, String betMessage) {
          this.betAmount = betAmount;
          this.player = player;
          this.betMessage = betMessage;
     }

     public long getId() {
          return id;
     }

     public int getBetAmount() {
          return betAmount;
     }

     public User getUser() {
          return player;
     }

     public String getBetMessage() {
          return betMessage;
     }
}
