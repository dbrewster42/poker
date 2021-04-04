package com.brewster.poker.model.response;

import com.brewster.poker.bets.BetOptions;
import com.brewster.poker.cards.Card;
import com.brewster.poker.dto.UserDto;

import java.util.List;

public class NewGameResponse {
    private final int gameId;
    private final List<Card> hand;
    private final List<UserDto> users;
    private final BetOptions betOptions;

//    public NewGameResponse(final String body, final int statusCode, final int bigBlindTurn, final List<Card> hand, final List<UserDto> users){
//        super(body, statusCode);
//        this.bigBlindTurn = bigBlindTurn;
//        this.hand = hand;
//        this.users = users;
//    }
    public NewGameResponse(final int gameId, final List<Card> hand, final List<UserDto> users, final BetOptions betOptions){
        this.gameId = gameId;
        this.hand = hand;
        this.users = users;
        this.betOptions = betOptions;
    }
    public NewGameResponse(final int gameId, final List<Card> hand, final List<UserDto> users){
        this.gameId = gameId;
        this.hand = hand;
        this.users = users;
        betOptions = null;
    }

//    public int getBigBlindTurn() {
//        return bigBlindTurn;
//    }


    public int getGameId() {
        return gameId;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public BetOptions getBetOptions() {
        return betOptions;
    }

    public List<Card> getHand() {
        return hand;
    }
}
