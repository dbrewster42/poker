package com.brewster.poker.game;

import com.brewster.poker.dto.PlayerDto;

import java.util.ArrayList;
import java.util.List;

public class Game {
    List<PlayerDto> players;
    int numberOfPlayers;
    int id;

    public Game(int id, List<PlayerDto> players){
        this.id = id;
        this.players = players;
        this.numberOfPlayers = players.size();
    }

//    public List<Response> dealNewGame(List<Player> players){
//
//    }
}
