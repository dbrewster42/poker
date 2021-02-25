package com.brewster.poker.service;

import com.brewster.poker.dto.PlayerDto;

public interface PlayerService {
//    Player findPlayer(String email);
    PlayerDto findAndUpdatePlayer(PlayerDto dto);
    PlayerDto createPlayer(PlayerDto dto);
}
