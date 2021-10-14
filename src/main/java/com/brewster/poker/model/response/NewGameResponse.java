package com.brewster.poker.model.response;

import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.card.Card;
import com.brewster.poker.dto.UserDto;

import java.util.List;

public class NewGameResponse {
    private long id;
    private final List<Card> hand;
    private List<UserDto> users;
    private BetOptions betOptions;
    private int userMoney;

    public NewGameResponse(final long id, final List<Card> hand, final List<UserDto> users, final BetOptions betOptions, final int userMoney){
        this.id = id;
        this.hand = hand;
        this.users = users;
        this.betOptions = betOptions;
        this.userMoney = userMoney;
    }
    public NewGameResponse(final int gameId, final List<Card> hand, final List<UserDto> users, final BetOptions betOptions, final int userMoney){
        this.id = gameId;
        this.hand = hand;
        this.users = users;
        this.betOptions = betOptions;
        this.userMoney = userMoney;
    }
    public NewGameResponse(final List<Card> hand, BetOptions betOptions, int userMoney, List<UserDto> users){
        this.hand = hand;
        this.betOptions = betOptions;
        this.userMoney = userMoney;
        this.users = users;
    }

//    public int getBigBlindTurn() {
//        return bigBlindTurn;
//    }


    public long getId() {
        return id;
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

    public int getUserMoney() {
        return userMoney;
    }
}
