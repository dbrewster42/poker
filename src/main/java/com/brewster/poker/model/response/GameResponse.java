package com.brewster.poker.model.response;

import com.brewster.poker.card.Card;
import com.brewster.poker.dto.PlayerDto;

import java.util.List;

public class GameResponse {
    private List<PlayerDto> playerDtos;
    private List<Card> riverCards;
    private boolean isOver;
    private EndRoundResponse endRoundResponse;

    public GameResponse(List<Card> riverCards){
        this.riverCards = riverCards;
        isOver = false;
    }
//    public GameResponse(List<PlayerDto> playerDtos){
//        this.playerDtos = playerDtos;
//        isOver = false;
//    }
    public GameResponse(List<PlayerDto> playerDtos, int extra){
        this.playerDtos = playerDtos;
        isOver = false;
    }

    public GameResponse(EndRoundResponse endRoundResponse){
        this.endRoundResponse = endRoundResponse;
        isOver = true;
    }

    public List<Card> getRiverCards() {
        return riverCards;
    }

    public List<PlayerDto> getPlayerDtos() {
        return playerDtos;
    }

    public boolean isOver() {
        return isOver;
    }

    public EndRoundResponse getEndRoundResponse() {
        return endRoundResponse;
    }
}
