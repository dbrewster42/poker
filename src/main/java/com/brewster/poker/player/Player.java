package com.brewster.poker.player;

import com.brewster.poker.service.BetService;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.card.Card;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.service.GameService;
import com.brewster.poker.game.PokerHand;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private List<Card> hand;
    private String displayName;
    private GameService game;
    private int money;
    private UserDto user;
    private PokerHand pokerHand;
    private int currentBetAmount = 0;

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

    public abstract void placeBet(List<Card> riverCards, BetOptions options, BetService betManager);
    public void collectWinnings(int pot){
        this.money += pot;
    }
    public abstract void joinGame();
    public abstract void leaveGame();

    public void dealCard(Card card){ this.hand.add(card); }

    public void betMoney(int moneyBet){
        this.money = this.money - moneyBet;
        this.currentBetAmount = moneyBet;
    }

    public int getCurrentBetAmount() {
        return currentBetAmount;
    }

    public void resetCurrentBetAmount() {
        this.currentBetAmount = 0;
    }

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

    public GameService getGame() {
        return game;
    }

    public void setGame(GameService game) {
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

    public PokerHand getPokerHand() {
        return pokerHand;
    }

    public void setPokerHand(PokerHand pokerHand) {
        this.pokerHand = pokerHand;
    }
}
