package com.brewster.poker.player;

import com.brewster.poker.dto.UserDto;

public class ComputerPlayer extends Player {
    private static int bank = 10000;

//    public ComputerPlayer(String displayName, UserDto userDto) {
//        super(displayName, userDto);
//    }
    public ComputerPlayer(String displayName, UserDto userDto) {
        super(displayName);
        setUser(userDto);
        setMoney(1000);
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
