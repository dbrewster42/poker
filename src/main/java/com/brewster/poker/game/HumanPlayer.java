package com.brewster.poker.game;

import com.brewster.poker.dto.UserDto;

public class HumanPlayer extends Player {
    //private UserDto user;
    //private int money;

    public HumanPlayer(String displayName, UserDto user) {
        super(displayName, user);
    }

    //TODO shared interface for human/computer or computer extends? interface I think. // does it need its own package? should the package be in game?

    @Override
    public void placeBet() {

    }

    @Override
    public void collectWinnings() {

    }

    @Override
    public void joinGame() {

    }

    @Override
    public void leaveGame() {

    }

//
//    public UserDto getUser() {
//        return user;
//    }
//
//    public void setUser(UserDto user) {
//        this.user = user;
//    }

}
