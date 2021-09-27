package com.brewster.poker.player;

import com.brewster.poker.card.Card;
import com.brewster.poker.card.PokerHand;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private List<Card> hand;
    private String email;
    private String displayName;
    private int money;
    private PokerHand pokerHand;
    private int currentBetAmount = 0;

    public Player() {
    }

    public Player(String displayName){
        this.displayName = displayName;
        hand = new ArrayList<>();
    }
//    public Player(String displayName, UserDto userDto){
//        this.displayName = displayName;
//        this.user = userDto;
//        this.money = userDto.getMoney();
//        hand = new ArrayList<>();
//    }

    public abstract void joinGame();
    public abstract void leaveGame();

    public void collectWinnings(int pot){
        this.money += pot;
    }

    public void dealCard(Card card){ this.hand.add(card); }

    public void setHoleCards(){}

    public void betMoney(int moneyBet){
        this.money = this.money - moneyBet;
        this.currentBetAmount += moneyBet;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public PokerHand getPokerHand() {
        return pokerHand;
    }

    public void setPokerHand(PokerHand pokerHand) {
        this.pokerHand = pokerHand;
    }
}
