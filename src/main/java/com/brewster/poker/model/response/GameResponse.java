package com.brewster.poker.model.response;

import com.brewster.poker.card.Card;
import com.brewster.poker.dto.UserDto;

import java.util.List;

public class GameResponse {
    private List<Card> riverCards;
    private boolean isOver;
    private EndRoundResponse endRoundResponse;

    public GameResponse(List<Card> riverCards){
        this.riverCards = riverCards;
        isOver = false;
    }

    public GameResponse(EndRoundResponse endRoundResponse){
        this.endRoundResponse = endRoundResponse;
        isOver = true;
    }

    public List<Card> getRiverCards() {
        return riverCards;
    }

    public boolean isOver() {
        return isOver;
    }

    public EndRoundResponse getEndRoundResponse() {
        return endRoundResponse;
    }
}
