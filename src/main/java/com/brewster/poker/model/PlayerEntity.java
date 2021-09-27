package com.brewster.poker.model;

import org.springframework.data.annotation.Id;


public class PlayerEntity {
     @Id
     private String email;
     private String displayName;
     private Integer money;

     public String getEmail() {
          return email;
     }

     public void setEmail(String email) {
          this.email = email;
     }

     public String getDisplayName() {
          return displayName;
     }

     public void setDisplayName(String displayName) {
          this.displayName = displayName;
     }

     public Integer getMoney() {
          return money;
     }

     public void setMoney(Integer money) {
          this.money = money;
     }
}
