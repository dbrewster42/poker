package com.brewster.poker.service;

import com.brewster.poker.game.Player;
import com.brewster.poker.repository.UserRepository;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDto findUser(String username){
        User user = userRepository.findByUsername(username);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(user, returnValue);

        return returnValue;
    }

    public UserDto addMoneyToUser(UserDto dto){
        User oldUser =  userRepository.findByUsername(dto.getUsername());
        oldUser.setMoney(oldUser.getMoney() + dto.getMoney());

        User updatedUser = userRepository.save(oldUser);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(updatedUser, returnValue);
        return returnValue;
    }

    public UserDto createUser(UserDto dto){
        System.out.println("Service.createUser() " + dto.getUsername() + dto.getMoney());
        User newUser = new User();
        BeanUtils.copyProperties(dto, newUser);
        System.out.println("Service copied " + newUser.getUsername() + newUser.getMoney());

        User savedUser = userRepository.save(newUser);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(savedUser, returnValue);

        return returnValue;
    }


    public List<UserDto> generateNComputerUsers(int numberOfUsers){
        List<UserDto> users = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numberOfUsers - 1; i++){
            UserDto computerUser = new UserDto();
            computerUser.setUsername("HAL" + random.nextInt(500));
            computerUser.setMoney(999);
            computerUser.setPlayer(new Player(computerUser.getUsername()));
            users.add(computerUser);
        }
        return users;
    }
//    public List<UserDto> gatherUsersForGame(UserDto dto, int numberOfUsers){
//        List<UserDto> players = new ArrayList<>();
//        players.add(dto);
//        Random random = new Random();
//        for (int i = 0; i < numberOfUsers - 1; i++){
//            UserDto computerUser = new UserDto();
//            //computerUser.setId(0);
//            computerUser.setUsername("HAL" + random.nextInt(500));
//            computerUser.setMoney(999);
//            players.add(computerUser);
//        }
//        return players;
//    }

}
