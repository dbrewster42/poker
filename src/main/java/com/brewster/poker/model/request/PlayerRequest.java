package com.brewster.poker.model.request;

public class PlayerRequest {
     private String name;
     private int money;

     public PlayerRequest(String name, int money) {
          this.name = name;
          this.money = money;
     }

     public PlayerRequest() {
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public int getMoney() {
          return money;
     }

     public void setMoney(int money) {
          this.money = money;
     }
}
