package com.brewster.poker.service;

import com.brewster.poker.model.PlayerRequest;
import com.brewster.poker.player.Player;

public interface PlayerService {
//    Player findPlayer(String email);
    Player findAndUpdatePlayer(PlayerRequest request);
}
