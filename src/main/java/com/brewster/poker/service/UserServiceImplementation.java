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

@Service
public class UserServiceImplementation implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImplementation.class);
    private final UserRepository userRepository;
    private final Utils utils;
//    @Autowired
//    private BetRepository betRepository;

    public UserServiceImplementation(UserRepository userRepository, Utils utils){
        this.userRepository = userRepository;
        this.utils = utils;
    }

    public UserDto findUser(String username){
        LOGGER.info("finding {}", username);
//        debug();
//        User user = Optional.ofNullable(userRepository.findByEmail(username))
//                .orElseThrow(UserNotFoundException::new);

        User user = findUserByEmail(username);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(user, returnValue);

        return returnValue;
    }

    public User findUserByEmail(String username){
        Optional<User> optionalUser = userRepository.findByEmail(username);
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
//        debug();
        System.out.println(dto.getEmail() + " - " + dto.getMoney());

        User newUser = new User();
        BeanUtils.copyProperties(dto, newUser);
        if (!utils.isEmailValid(newUser)){
            throw new IllegalArgumentException("invalid email");
        }
        newUser.setId(utils.generateRandomString(12));

        User savedUser = userRepository.save(newUser);
        System.out.println(savedUser.toString());
        Optional.ofNullable(savedUser).orElseThrow(() -> new RuntimeException("user not saved correctly"));

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(savedUser, returnValue);

        return returnValue;
    }

//    private void debug(){
//        LOGGER.info("all users -");
//        for (User each : userRepository.findAll()){
//            LOGGER.info(each.toString());
//        }
//    }

    public List<UserDto> findAllUsers(){
        return new ArrayList<>();
    }

    public void updateUsersMoney(List<Player> players){
        for (Player player : players){
            if (player instanceof  HumanPlayer){
                User user = findUserByEmail(player.getEmail());
                user.setMoney(player.getMoney());
                userRepository.save(user);
            }
        }
//        players.stream().filter(v -> v instanceof HumanPlayer)
////                .map(v -> new User(v.getUser()))
//                .map(v -> findUserByEmail(v.getUser().getEmail()))
//                .forEach(userRepository::save);
    }

//
//    public List<BetEntity> getUserBets(String username){
//        User user = userRepository.findByUsername(username);
//
//        return Optional.ofNullable(user).map(betRepository::findAllByUser)
//                .orElseThrow(UserNotFoundException::new);
//    }

}
