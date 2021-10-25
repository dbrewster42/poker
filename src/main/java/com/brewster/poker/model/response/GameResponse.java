package com.brewster.poker.model.response;

import com.brewster.poker.card.Card;
import com.brewster.poker.dto.PlayerDto;

import java.util.List;

public class GameResponse {
    private List<PlayerDto> playerDtos;
    private List<Card> riverCards;
    private boolean isOver;
    private EndRoundResponse endRoundResponse;
    private boolean isLastTurn;

    public GameResponse(List<Card> riverCards){
        this.riverCards = riverCards;
        isOver = false;
    }
    public GameResponse(List<PlayerDto> playerDtos, boolean isLastTurn){
        this.playerDtos = playerDtos;
        this.isLastTurn = isLastTurn;
        isOver = false;
    }
    public GameResponse(EndRoundResponse endRoundResponse){
        this.endRoundResponse = endRoundResponse;
        isOver = true;
    }

    public boolean isLastTurn() {
        return isLastTurn;
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
