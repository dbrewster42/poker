package com.brewster.poker.game;

import com.brewster.poker.dto.PlayerDto;

import java.util.ArrayList;
import java.util.List;

public class BetManager {
    private Game game;
    private List<PlayerDto> players;
    private int bigBlind;
    private int smallBlind;
    private int turn;
    private int minimumBet;
    private int betAmount;
    private List<Action> possibleActions;
    //TODO bet container?

    public BetManager(Game game, int bigBlind) {
        this.game = game;
        this.players = game.getPlayers();
        this.bigBlind = bigBlind;
        this.smallBlind = bigBlind / 2;
        this.turn = 0;
        this.possibleActions = new ArrayList<>();
    }

    public void newRound(){
        // players.
        //TODO where to keep Bets, part of playerDto's? own separate list?
    }

    public List<Action> getPossibleBetActions(){
        return possibleActions;
    }
}
