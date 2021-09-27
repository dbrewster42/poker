package com.brewster.poker.model.request;

public class PlayerRequest {
     private String email;
     private String displayName;
     private int money;
     private char[] password;

     public PlayerRequest(String email, String displayName, int money) {
          this.email = email;
          this.displayName = displayName;
          this.money = money;
     }

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

     public int getMoney() {
          return money;
     }

     public void setMoney(int money) {
          this.money = money;
     }

     public char[] getPassword() {
          return password;
     }

     public void setPassword(char[] password) {
          this.password = password;
     }

     @Override
     public String toString() {
          return "PlayerRequest{" + "email='" + email + '\'' + ", displayName='" + displayName + '\'' + ", money=" + money + '}';
     }
}
