package com.brewster.poker.card;

import java.util.ArrayList;
import java.util.List;

public class DeckBuilder {
    private final static String[] SUITS = { "Clubs", "Hearts", "Spades", "Diamonds" };
    private final static String[] NAMES = { "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King" };
    private final static int[] VALUES = { 14, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
    private final String[] IMAGE_PREFIXES = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
    private final Deck deck;
    private List<Card> cards = new ArrayList<>();

    public DeckBuilder(){
        deck = new Deck();
    }
    public DeckBuilder(int wildcards){
        deck = new Deck();
    }
    public DeckBuilder(List<String> customRules){
        deck = new Deck();
    }

    public static DeckBuilder aDeck(){
        return new DeckBuilder();
    }

    public Deck build(){
        deck.shuffle();
        return deck;
    }

    public DeckBuilder withJokersDeck(int wildcards) {
        cards = buildStandardDeck();
        Card joker = new Card("any", 20, "Joker", "purple_back");
        for (int i = 0; i < wildcards; i++) {
            cards.add(joker);
        }
        deck.setCards(cards);
        return this;
    }

    public DeckBuilder withStandardDeck(){
        deck.setCards(buildStandardDeck());
        return this;
    }

    public List<Card> buildStandardDeck(){
        for (int i = 0; i < 13; i++){
            for (int j = 0; j < 4; j++){
                Card card = new Card(SUITS[j], VALUES[i], NAMES[i], IMAGE_PREFIXES[i] + SUITS[j].substring(0, 1));
                cards.add(card);
            }
        }
        return cards;
    }

    public static String[] getSUITS() {
        return SUITS;
    }
}
