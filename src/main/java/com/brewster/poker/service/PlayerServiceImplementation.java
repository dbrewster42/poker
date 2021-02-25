package com.brewster.poker.service;

import com.brewster.poker.dao.PlayerRepository;
import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.model.Player;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImplementation implements PlayerService {
    private final PlayerRepository playerRepository;
    public PlayerServiceImplementation(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

//    public Player findPlayer(String email){
//        return new Player(email, 10);
//    }
    public PlayerDto findAndUpdatePlayer(PlayerDto dto){
        Player oldPlayer = new Player();
        BeanUtils.copyProperties(dto, oldPlayer);
        return dto;
    }

    public PlayerDto createPlayer(PlayerDto dto){
        Player newPlayer = new Player();
        BeanUtils.copyProperties(dto, newPlayer);

        Player savedPlayer = playerRepository.save(newPlayer);

        PlayerDto returnValue = new PlayerDto();
        BeanUtils.copyProperties(savedPlayer, returnValue);

        return returnValue;
    }

}
