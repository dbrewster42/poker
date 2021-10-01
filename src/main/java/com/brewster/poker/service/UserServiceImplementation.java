package com.brewster.poker.service;

import com.brewster.poker.exception.UserNotFoundException;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;
import com.brewster.poker.repository.UserRepository;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDto findUser(String username){
        User user = Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new UserNotFoundException());

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(user, returnValue);

        return returnValue;
    }

    public UserDto addMoneyToUser(UserDto dto){
        User oldUser =  Optional.ofNullable(userRepository.findByUsername(dto.getUsername()))
                .orElseThrow(() -> new UserNotFoundException());
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
//        Optional.ofNullable(savedUser).orElseThrow(() -> new RuntimeException("user not saved correctly"));

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(savedUser, returnValue);

        return returnValue;
    }

    public void updateUsersMoney(List<Player> players){
        players.stream().filter(v -> v instanceof HumanPlayer)
                .map(v -> new User(v.getUser()))
                .forEach(v -> userRepository.save(v));
    }

}
