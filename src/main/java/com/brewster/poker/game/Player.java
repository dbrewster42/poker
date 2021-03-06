package com.brewster.poker.game;

import com.brewster.poker.card.Card;
import com.brewster.poker.game.bet.Bet;
import com.brewster.poker.model.User;

import java.util.List;

public class Player {
    private List<Card> hand;
    private Bet bet; //TODO bet or List<Bet>?
    private String displayName;
    private User user;

    //TODO shared interface for human/computer or computer extends? interface I think. // does it need its own package? should the package be in game?

    public Player(String displayName){
        this.displayName = displayName;
    }

    public void dealCard(Card card){ this.hand.add(card); }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
