package com.brewster.poker.player;

import com.brewster.poker.card.Card;
import com.brewster.poker.card.PokerHandEnum;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private List<Card> cards;
    private String displayName;
    private int money;
    private PokerHandEnum pokerHand;
    private int currentBetAmount = 0;
    private String email;

    public Player(String displayName, String email){
        this.displayName = displayName;
        this.email = email;
    }

    public abstract void joinGame();
    public abstract void leaveGame();

    public void collectWinnings(int pot){
        this.money += pot;
//        this.user.setMoney(this.money);
    }

    public void betMoney(int moneyBet){
        this.money = this.money - moneyBet;
        this.currentBetAmount += moneyBet;
//        this.user.setMoney(this.money);
    }

    public void dealCard(Card card){ this.cards.add(card); }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void resetCards() {
        this.cards = new ArrayList<>();
    }

    public int getCurrentBetAmount() {
        return currentBetAmount;
    }

    public void resetCurrentBetAmount() {
        this.currentBetAmount = 0;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getEmail() {
        return email;
    }

    public PokerHandEnum getPokerHand() {
        return pokerHand;
    }

    public void setPokerHand(PokerHandEnum pokerHand) {
        this.pokerHand = pokerHand;
    }
}
