package com.brewster.poker.service;

import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.exception.UserNotFoundException;
import com.brewster.poker.model.PlayerEntity;
import com.brewster.poker.repository.PlayerRepository;
import com.brewster.poker.utility.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
     private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);
     private final PlayerRepository playerRepository;
     private final Utils utils;

     public PlayerService(PlayerRepository playerRepository, Utils utils){
          this.playerRepository = playerRepository;
          this.utils = utils;
     }

     public PlayerDto createPlayer(PlayerDto dto){
          LOGGER.info(dto.toString());
          if (!utils.isEmailValid(dto)){
               throw new IllegalArgumentException("Email is invalid");
          }

          PlayerEntity newPlayer = new PlayerEntity();
          BeanUtils.copyProperties(dto, newPlayer);

          PlayerEntity savedPlayer = playerRepository.save(newPlayer);
          Optional.ofNullable(savedPlayer).orElseThrow(() -> new RuntimeException("user not saved correctly"));
          LOGGER.info(savedPlayer.toString());

          PlayerDto returnValue = new PlayerDto();
          BeanUtils.copyProperties(savedPlayer, returnValue);

          return returnValue;
     }

     public PlayerDto findPlayer(String username){
          PlayerEntity playerEntity = Optional.ofNullable(playerRepository.findByUsername(username)).get()
                  .orElseThrow(() -> new UserNotFoundException());

          PlayerDto returnValue = new PlayerDto();
          BeanUtils.copyProperties(playerEntity, returnValue);

          return returnValue;
     }

}
