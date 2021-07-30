package com.brewster.poker.player;

import com.brewster.poker.bets.BetManager;
import com.brewster.poker.bets.BetOptions;
import com.brewster.poker.cards.Card;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.game.Game;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private List<Card> hand;
    private String displayName;
    private Game game;
    private int money;
    private UserDto user;

    public Player(String displayName){
        this.displayName = displayName;
        hand = new ArrayList<>();
    }
    public Player(String displayName, UserDto userDto){
        this.displayName = displayName;
        this.user = userDto;
        this.money = userDto.getMoney();
        hand = new ArrayList<>();
    }

    public abstract void placeBet(List<Card> riverCards, BetOptions options, BetManager betManager);
    public abstract void collectWinnings();
    public abstract void joinGame();
    public abstract void leaveGame();

    public void dealCard(Card card){ this.hand.add(card); }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
