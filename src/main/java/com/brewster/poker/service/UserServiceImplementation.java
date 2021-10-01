package com.brewster.poker.service;

import com.brewster.poker.exception.UserNotFoundException;
import com.brewster.poker.model.BetEntity;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;
import com.brewster.poker.repository.BetRepository;
import com.brewster.poker.repository.UserRepository;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    @Autowired
    private BetRepository betRepository;

    public UserServiceImplementation(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDto findUser(String username){
        User user = Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(UserNotFoundException::new);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(user, returnValue);

        return returnValue;
    }

    public UserDto addMoneyToUser(UserDto dto){
        User oldUser =  Optional.ofNullable(userRepository.findByUsername(dto.getUsername()))
                .orElseThrow(UserNotFoundException::new);
        int updatedMoney = oldUser.getMoney() + dto.getMoney();
        oldUser.setMoney(updatedMoney);
        Optional.ofNullable(oldUser).filter(v -> v.getMoney() == updatedMoney)
                .orElseThrow(() -> new RuntimeException("user's money was not saved correctly"));


        User updatedUser = userRepository.save(oldUser);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(updatedUser, returnValue);
        return returnValue;
    }

    public UserDto createUser(UserDto dto){
        User newUser = new User();
        BeanUtils.copyProperties(dto, newUser);

        User savedUser = userRepository.save(newUser);
        Optional.ofNullable(savedUser).orElseThrow(() -> new RuntimeException("user not saved correctly"));

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(savedUser, returnValue);

        return returnValue;
    }

    public void updateUsersMoney(List<Player> players){
        players.stream().filter(v -> v instanceof HumanPlayer)
//                .map(v -> new User(v.getUser()))
                .map(v -> userRepository.findByUsername(v.getUser().getUsername()))
                .forEach(userRepository::save);
    }


    public List<BetEntity> getUserBets(String username){
        User user = userRepository.findByUsername(username);
        Optional.ofNullable(user).orElseThrow(UserNotFoundException::new);

        return betRepository.findAllByUser(user);
    }

}
