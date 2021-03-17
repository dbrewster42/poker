package com.brewster.poker.player;

import com.brewster.poker.dto.UserDto;
import com.brewster.poker.player.Player;

public class HumanPlayer extends Player {

    public HumanPlayer(String displayName, UserDto user) {
        super(displayName, user);
    }

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

}
