package com.brewster.poker.model.response;

import com.brewster.poker.player.Player;

public class PlayerResponse {
     private String name;
     private int money;

     public PlayerResponse(){}

     public PlayerResponse(Player player){
          this.name = player.getDisplayName();
          this.money = player.getMoney();
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
