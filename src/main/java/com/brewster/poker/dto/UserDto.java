package com.brewster.poker.dto;

import com.brewster.poker.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Transient;

public class UserDto {
    private String email;
    private String displayName;
    private Integer money;
    @JsonIgnore
    private String id;
    @JsonIgnore
    @Transient
    private Player player;

    public UserDto(Player player) {
        this.email = player.getEmail();
        this.displayName = player.getDisplayName();
        this.money = player.getMoney();
        this.player = player;
    }
    public UserDto() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", username='" + email + '\'' +
                ", money=" + money +
                '}';
    }
}
