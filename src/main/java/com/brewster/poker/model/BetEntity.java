package com.brewster.poker.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BetEntity {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private long id;
     private int betAmount;
     @ManyToOne
     private User user;
     private String betMessage;

     public BetEntity(int betAmount, User user, String betMessage) {
          this.betAmount = betAmount;
          this.user = user;
          this.betMessage = betMessage;
     }

     public long getId() {
          return id;
     }

     public int getBetAmount() {
          return betAmount;
     }

     public User getUser() {
          return user;
     }

     public String getBetMessage() {
          return betMessage;
     }
}
