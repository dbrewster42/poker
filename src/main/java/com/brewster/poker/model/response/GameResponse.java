package com.brewster.poker.model.response;

import com.brewster.poker.card.Card;
import com.brewster.poker.dto.UserDto;

import java.util.List;

public class GameResponse {
    private List<Card> riverCards;
    private boolean isGameOver;
//    private int bigBlindTurn;
//    private int betTurn;

//    public GameResponse(List<UserDto> users, int bigBlindTurn, int betTurn) {
//        this.bigBlindTurn = bigBlindTurn;
//        this.betTurn = betTurn;
//    }

    public GameResponse(List<Card> riverCards){
        this.riverCards = riverCards;
        isGameOver = false;
    }

    public List<Card> getRiverCards() {
        return riverCards;
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}
