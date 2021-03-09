package com.brewster.poker.game;

import com.brewster.poker.card.Card;

import java.util.List;

public class Player {
    private List<Card> hand;
    private Bet bet;
    //TODO shared interface for human/computer or computer extends? interface I think. // does it need its own package? should the package be in game?


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
}
