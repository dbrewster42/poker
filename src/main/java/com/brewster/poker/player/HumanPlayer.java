package com.brewster.poker.player;

import com.brewster.poker.dto.UserDto;


public class HumanPlayer extends Player {

    public HumanPlayer(String displayName, UserDto user) {
        super(displayName, user);
    }
    public HumanPlayer(String displayName) {
        super(displayName);
    }

    @Override
    public void joinGame() {

    }

    @Override
    public void leaveGame() {

    }

}
