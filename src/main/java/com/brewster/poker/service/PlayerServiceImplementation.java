package com.brewster.poker.service;

import com.brewster.poker.dao.PlayerRepository;
import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.model.Player;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerServiceImplementation implements PlayerService {
    private final PlayerRepository playerRepository;
    public PlayerServiceImplementation(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

//    public Player findPlayer(String email){
//        return new Player(email, 10);
//    }
    public List<PlayerDto> startGame(PlayerDto dto, int numberOfPlayers){
        List<PlayerDto> players = new ArrayList<>();
        players.add(dto);
        for (int i = 0; i < numberOfPlayers - 1; i++){
            PlayerDto computerPlayer = new PlayerDto();
            computerPlayer.setUsername("HAL");
            computerPlayer.setMoney(999);
            players.add(computerPlayer);
        }

        return players;
    }

    public PlayerDto findAndUpdatePlayer(PlayerDto dto){
        Player oldPlayer = new Player();
        BeanUtils.copyProperties(dto, oldPlayer);
        return dto;
    }

    public PlayerDto createPlayer(PlayerDto dto){
        System.out.println("Service.createPlayer() " + dto.getUsername() + dto.getMoney());
        Player newPlayer = new Player();
        BeanUtils.copyProperties(dto, newPlayer);
        System.out.println("Service copied " + newPlayer.getUsername() + newPlayer.getMoney());

        Player savedPlayer = playerRepository.save(newPlayer);

        PlayerDto returnValue = new PlayerDto();
        BeanUtils.copyProperties(savedPlayer, returnValue);

        return returnValue;
    }

}
