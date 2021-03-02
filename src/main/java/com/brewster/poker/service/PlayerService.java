package com.brewster.poker.service;

import com.brewster.poker.dto.PlayerDto;

import java.util.List;

public interface PlayerService {
    PlayerDto findPlayer(String username);
    PlayerDto findAndUpdatePlayer(PlayerDto dto);
    PlayerDto createPlayer(PlayerDto dto);
    List<PlayerDto> startGame(PlayerDto dto, int players);
}
