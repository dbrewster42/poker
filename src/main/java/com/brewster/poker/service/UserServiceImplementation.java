package com.brewster.poker.service;

import com.brewster.poker.repository.UserRepository;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDto findUser(String username){
        User user = userRepository.findByUsername(username);
        System.out.println(user.getUsername() + " - " + user.getMoney() + " - " + user.getId());
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
}
