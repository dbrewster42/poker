package com.brewster.poker.dto;

import com.brewster.poker.player.HumanPlayer;

import java.util.List;

public class GameDto {
    private int id;
    private List<HumanPlayer> players;
    private boolean customRules;
    private Integer numberOfPlayers;
    private Integer bigBlind;
    private Integer maxBet;


}
