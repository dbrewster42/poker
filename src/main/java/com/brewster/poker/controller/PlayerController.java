package com.brewster.poker.controller;

import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.model.request.UserRequest;
import com.brewster.poker.model.response.PlayerResponse;
import com.brewster.poker.service.PlayerService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
public class PlayerController {
     private PlayerService playerService;

     public PlayerController(PlayerService playerService){
          this.playerService = playerService;
     }

     @PostMapping("register")
     public PlayerResponse register(@RequestBody UserRequest request) {
          PlayerDto dto = new PlayerDto();
          BeanUtils.copyProperties(request, dto);

          dto = playerService.createPlayer(dto);

          PlayerResponse returnValue = new PlayerResponse();
          BeanUtils.copyProperties(dto, returnValue);

          return returnValue;
     }

//     @PostMapping("login")
//     public UserDto login(@RequestBody UserRequest request) {
//          return playerService.findUser(request.getUsername());
//     }
//
//     @PutMapping("buyin")
//     public UserDto addMoney(@RequestBody UserRequest request){
//          UserDto dto = new UserDto();
//          BeanUtils.copyProperties(request, dto);
//
//          return playerService.addMoneyToUser(dto);
//     }
}
