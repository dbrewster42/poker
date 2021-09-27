package com.brewster.poker.model.response;

import com.brewster.poker.player.Player;

public class PlayerResponse {
     private String displayName;
     private int money;

     public PlayerResponse(){}

     public PlayerResponse(Player player){
          this.displayName = player.getDisplayName();
          this.money = player.getMoney();
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
}
