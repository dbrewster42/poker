package com.brewster.poker.controller;

import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
public class PlayerController {
     private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);
     private PlayerService playerService;

     public PlayerController(PlayerService playerService){
          this.playerService = playerService;
     }

     @PostMapping("register")
     public PlayerDto register(@RequestBody PlayerDto request) {
          LOGGER.info(request.toString());

          PlayerDto savedDto = playerService.createPlayer(request);
          LOGGER.info(savedDto.toString());

          return savedDto;
     }

     @PostMapping("login")
     public PlayerDto login(@RequestBody PlayerDto request) {
          return playerService.findPlayer(request.getUsername());
     }

//     @PutMapping("buyin")
//     public UserDto addMoney(@RequestBody UserRequest request){
//          UserDto dto = new UserDto();
//          BeanUtils.copyProperties(request, dto);
//
//          return playerService.addMoneyToUser(dto);
//     }
}
