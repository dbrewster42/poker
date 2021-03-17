package com.brewster.poker.dto;

import com.brewster.poker.game.HumanPlayer;

public class UserDto {
    private Integer id;
    private String username;
    private Integer money;
    //private HumanPlayer player;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
//
//    public HumanPlayer getPlayer() {
//        return player;
//    }
//
//    public void setPlayer(HumanPlayer player) {
//        this.player = player;
//    }
//

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", money=" + money +
                '}';
    }
}
