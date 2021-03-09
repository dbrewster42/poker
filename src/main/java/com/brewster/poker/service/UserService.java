package com.brewster.poker.service;

import com.brewster.poker.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto findPlayer(String username);
    UserDto addMoneyToPlayer(UserDto dto);
    UserDto createPlayer(UserDto dto);
    List<UserDto> generateNComputerPlayers(int numberOfPlayers);
    //List<PlayerDto> gatherPlayersForGame(PlayerDto dto, int players);
}
