package com.brewster.poker.card;

import java.util.List;

public class DeckBuilder {
    private final static String[] SUITS = { "Clubs", "Hearts", "Spades", "Diamonds" };
    private final static String[] NAMES = { "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King" };
    private final static int[] VALUES = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
    private final Deck deck;
    private Card[] cards;

    public DeckBuilder(){
        deck = new Deck();
        cards = new Card[52];
        deck.setCards(buildStandardDeck());
        deck.shuffle();
    }
    public DeckBuilder(int wildcards){
        deck = new Deck();
        cards = new Card[52 + wildcards];
        deck.setCards(buildDeckWithJokers(wildcards));
        deck.shuffle();
    }
    public DeckBuilder(List<String> customRules){
        deck = new Deck();
        //deck.setCards(buildDeckWithJokers(wildcards));
    }


    public Card[] buildDeckWithJokers(int wildcards){
        cards = buildStandardDeck();
        Card joker = new Card("any", 20, "Joker");
        int count = 52;
        for (int i = 0; i < wildcards; i++){
            cards[count] = joker;
        }
        return cards;
    }

    public Card[] buildStandardDeck(){
        int count = 0;
        for (int i = 0; i < 13; i++){
            for (int j = 0; j < 4; j++){
                Card card = new Card(SUITS[j], VALUES[i], NAMES[i]);
                cards[count] = card;
                count++;
            }
        }
        return cards;
    }
}
