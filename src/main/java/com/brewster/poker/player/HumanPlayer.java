package com.brewster.poker.player;

import com.brewster.poker.dto.UserDto;


public class HumanPlayer extends Player {

    public HumanPlayer(String displayName, UserDto user) {
        super(displayName, user);
    }

    public HumanPlayer(String displayName, String email) {
        super(displayName, email);
    }


    @Override
    public void joinGame() {

    }

    @Override
    public void leaveGame() {

    }

}
