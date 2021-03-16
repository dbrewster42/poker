package com.brewster.poker.dto;

import com.brewster.poker.game.Player;

import java.util.List;

public class GameDto {
    private int id;
    private List<Player> players;
    private boolean customRules;
    private Integer numberOfPlayers;
    private Integer bigBlind;
    private Integer maxBet;
}
