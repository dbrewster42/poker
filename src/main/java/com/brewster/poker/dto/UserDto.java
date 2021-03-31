package com.brewster.poker.dto;

import com.brewster.poker.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDto {
    private String username;
    private Integer money;
    @JsonIgnore
    private Integer id;
    @JsonIgnore
    private Player player;


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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", money=" + money +
                '}';
    }
}
