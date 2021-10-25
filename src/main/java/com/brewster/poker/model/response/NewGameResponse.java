package com.brewster.poker.model.response;

import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.card.Card;
import com.brewster.poker.dto.Dto;
import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class NewGameResponse {
    private long gameId;
    private final List<Card> hand;
    private BetOptions betOptions;
    private int userMoney;
    private List<Dto> users;
    private List<PlayerDto> playerDtos;

    public NewGameResponse(final long id, final List<Card> hand, final List<UserDto> users, final BetOptions betOptions, final int userMoney){
        this.gameId = id;
        this.hand = hand;
        this.betOptions = betOptions;
        this.userMoney = userMoney;
        this.users = users.stream().map(v -> (Dto) v).collect(Collectors.toList());
    }
    public NewGameResponse(final long id, final List<Card> hand, final BetOptions betOptions, final int userMoney, final List<PlayerDto> users){
        this.gameId = id;
        this.hand = hand;
        this.betOptions = betOptions;
        this.userMoney = userMoney;
        this.users = users.stream().map(v -> (Dto) v).collect(Collectors.toList());
    }


    public long getGameId() {
        return gameId;
    }

    public List<Dto> getUsers() {
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
