package com.brewster.poker.controller;

import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.request.UserRequest;
import com.brewster.poker.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody UserRequest request) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(request, dto);

        return userService.createUser(dto);
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody UserRequest request) {
        return userService.findUser(request.getUsername());
    }

    @PutMapping("buyin")
    public UserDto addMoney(@RequestBody UserRequest request){
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(request, dto);

        return userService.addMoneyToUser(dto);
    }


}
