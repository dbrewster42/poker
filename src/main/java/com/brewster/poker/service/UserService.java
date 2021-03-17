package com.brewster.poker.service;

import com.brewster.poker.dto.UserDto;


public interface UserService {
    UserDto findUser(String username);
    UserDto addMoneyToUser(UserDto dto);
    UserDto createUser(UserDto dto);
//    UserDto getComputerUser(UserDto dto);
//    List<UserDto> generateNComputerUsers(int numberOfUsers);
    //List<UserDto> gatherUsersForGame(UserDto dto, int players);
}
