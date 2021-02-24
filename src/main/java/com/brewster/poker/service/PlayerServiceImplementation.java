package com.brewster.poker.service;

import com.brewster.poker.model.PlayerRequest;
import com.brewster.poker.player.Player;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImplementation implements PlayerService {
//    public Player findPlayer(String email){
//        return new Player(email, 10);
//    }
    public Player findAndUpdatePlayer(PlayerRequest request){
        return new Player(request);
    }
}
