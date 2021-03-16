package com.brewster.poker.service;

import com.brewster.poker.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto findUser(String username);
    UserDto addMoneyToUser(UserDto dto);
    UserDto createUser(UserDto dto);
//    List<UserDto> generateNComputerUsers(int numberOfUsers);
    //List<UserDto> gatherUsersForGame(UserDto dto, int players);
}
