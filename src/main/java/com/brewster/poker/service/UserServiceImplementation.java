package com.brewster.poker.service;

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

    public UserDto findPlayer(String username){
        User user = userRepository.findByUsername(username);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(user, returnValue);

        return returnValue;
    }

    public UserDto addMoneyToPlayer(UserDto dto){
        User oldUser =  userRepository.findByUsername(dto.getUsername());
        oldUser.setMoney(oldUser.getMoney() + dto.getMoney());

        User updatedUser = userRepository.save(oldUser);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(updatedUser, returnValue);
        return returnValue;
    }

    public UserDto createPlayer(UserDto dto){
        System.out.println("Service.createPlayer() " + dto.getUsername() + dto.getMoney());
        User newUser = new User();
        BeanUtils.copyProperties(dto, newUser);
        System.out.println("Service copied " + newUser.getUsername() + newUser.getMoney());

        User savedUser = userRepository.save(newUser);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(savedUser, returnValue);

        return returnValue;
    }


    public List<UserDto> generateNComputerPlayers(int numberOfPlayers){
        List<UserDto> players = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numberOfPlayers - 1; i++){
            UserDto computerPlayer = new UserDto();
            computerPlayer.setUsername("HAL" + random.nextInt(500));
            computerPlayer.setMoney(999);
            players.add(computerPlayer);
        }
        return players;
    }
//    public List<PlayerDto> gatherPlayersForGame(PlayerDto dto, int numberOfPlayers){
//        List<PlayerDto> players = new ArrayList<>();
//        players.add(dto);
//        Random random = new Random();
//        for (int i = 0; i < numberOfPlayers - 1; i++){
//            PlayerDto computerPlayer = new PlayerDto();
//            //computerPlayer.setId(0);
//            computerPlayer.setUsername("HAL" + random.nextInt(500));
//            computerPlayer.setMoney(999);
//            players.add(computerPlayer);
//        }
//        return players;
//    }

}
