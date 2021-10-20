package com.brewster.poker.service;

import com.brewster.poker.dto.UserDto;
import com.brewster.poker.player.Player;

import java.util.List;

public interface UserService {
    UserDto findUserDtoByEmail(String username);
    UserDto retrieveComputerUser();
    UserDto addMoneyToUser(UserDto dto);
    UserDto createUser(UserDto dto);
    void updateAllPlayersMoney(List<Player> players);
    List<UserDto> findAllUsers();
//    List<BetEntity> getUserBets(String username);
}
