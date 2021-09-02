package com.brewster.poker.model.response;

import com.brewster.poker.card.Card;
import com.brewster.poker.dto.UserDto;

import java.util.List;

public class GameResponse {
    private List<UserDto> users;
    private List<Card> riverCards;
    private int bigBlindTurn;
    private int betTurn;

    public GameResponse(List<UserDto> users, int bigBlindTurn, int betTurn) {
        this.users = users;
        this.bigBlindTurn = bigBlindTurn;
        this.betTurn = betTurn;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }

    public int getBigBlindTurn() {
        return bigBlindTurn;
    }

    public void setBigBlindTurn(int bigBlindTurn) {
        this.bigBlindTurn = bigBlindTurn;
    }

    public int getBetTurn() {
        return betTurn;
    }

    public void setBetTurn(int betTurn) {
        this.betTurn = betTurn;
    }
}
