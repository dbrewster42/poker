package com.brewster.poker.service;

import com.brewster.poker.dto.UserDto;
import com.brewster.poker.player.Player;

import java.util.List;

public interface UserService {
    UserDto findUser(String username);
    UserDto addMoneyToUser(UserDto dto);
    UserDto createUser(UserDto dto);
    void updateUsersMoney(List<Player> players);
}
