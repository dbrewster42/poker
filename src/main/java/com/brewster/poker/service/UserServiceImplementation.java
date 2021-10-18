package com.brewster.poker.service;

import com.brewster.poker.dto.UserDto;
import com.brewster.poker.exception.UserNotFoundException;
import com.brewster.poker.model.User;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;
import com.brewster.poker.repository.UserRepository;
import com.brewster.poker.utility.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImplementation.class);
    private final UserRepository userRepository;
    private final Utils utils;


    public UserServiceImplementation(UserRepository userRepository, Utils utils){
        this.userRepository = userRepository;
        this.utils = utils;
    }

    public UserDto findUserDtoByEmail(String email){
        User user = findUserByEmail(email);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(user, returnValue);

        return returnValue;
    }

    public User findUserByEmail(String email){
        LOGGER.info("finding {}", email);

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException();
        }
        return optionalUser.get();
    }

    public UserDto addMoneyToUser(UserDto dto){
        User oldUser = findUserByEmail(dto.getEmail());
//                Optional.ofNullable(userRepository.findByEmail(dto.getEmail()))
//                .orElseThrow(UserNotFoundException::new);
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
        LOGGER.info("{} - {}", dto.getEmail(), dto.getMoney());

        User newUser = new User();
        BeanUtils.copyProperties(dto, newUser);
        if (!utils.isEmailValid(newUser)){
            throw new IllegalArgumentException("invalid email");
        }
        newUser.setId(utils.generateRandomString(12));

        User savedUser = userRepository.save(newUser);
        Optional.ofNullable(savedUser).orElseThrow(() -> new RuntimeException("user not saved correctly"));

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(savedUser, returnValue);

        return returnValue;
    }

    public List<UserDto> findAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(v -> new UserDto(v)).collect(Collectors.toList());
    }

    public void updateAllPlayersMoney(List<Player> players){
        for (Player player : players){
            if (player instanceof  HumanPlayer){
                LOGGER.info("saving money of {}", player.getMoney());
                User user = findUserByEmail(player.getEmail());
                user.setMoney(player.getMoney());
                userRepository.save(user);
            }
        }
    }

//
//    public List<BetEntity> getUserBets(String username){
//        User user = userRepository.findByUsername(username);
//
//        return Optional.ofNullable(user).map(betRepository::findAllByUser)
//                .orElseThrow(UserNotFoundException::new);
//    }

}
