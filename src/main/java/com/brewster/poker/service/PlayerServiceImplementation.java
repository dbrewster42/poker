package com.brewster.poker.service;

import com.brewster.poker.dao.PlayerRepository;
import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.model.Player;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PlayerServiceImplementation implements PlayerService {
    private final PlayerRepository playerRepository;
    public PlayerServiceImplementation(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    public List<PlayerDto> startGame(PlayerDto dto, int numberOfPlayers){
        List<PlayerDto> players = new ArrayList<>();
        players.add(dto);
        Random random = new Random();
        for (int i = 0; i < numberOfPlayers - 1; i++){
            PlayerDto computerPlayer = new PlayerDto();
            //computerPlayer.setId(0);
            computerPlayer.setUsername("HAL" + random.nextInt(500));
            computerPlayer.setMoney(999);
            players.add(computerPlayer);
        }

        return players;
    }

    public PlayerDto findPlayer(String username){
        Player player = playerRepository.findByUsername(username);

        PlayerDto returnValue = new PlayerDto();
        BeanUtils.copyProperties(player, returnValue);

        return returnValue;
    }

    public PlayerDto addMoneyToPlayer(PlayerDto dto){
        Player oldPlayer =  playerRepository.findByUsername(dto.getUsername());
        oldPlayer.setMoney(oldPlayer.getMoney() + dto.getMoney());

        Player updatedPlayer = playerRepository.save(oldPlayer);

        PlayerDto returnValue = new PlayerDto();
        BeanUtils.copyProperties(updatedPlayer, returnValue);
        return returnValue;
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
