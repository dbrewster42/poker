package com.brewster.poker.player;


import com.brewster.poker.dto.UserDto;

public class HumanPlayer extends Player {

    public HumanPlayer(String displayName, UserDto userDto) {
        super(displayName, userDto);
    }
    public HumanPlayer(String displayName, String email, int money){
        super(displayName, email, money);
    }


    @Override
    public void joinGame() {

    }

    @Override
    public void leaveGame() {

    }

}
