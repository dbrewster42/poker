package com.brewster.poker.game;

import com.brewster.poker.card.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private List<Card> hand;
    private String displayName;
    private Game game;

    public Player(String displayName){
        this.displayName = displayName;
        hand = new ArrayList<>();
    }

    public abstract void placeBet();
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
}
