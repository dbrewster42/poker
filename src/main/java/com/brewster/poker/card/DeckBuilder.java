package com.brewster.poker.card;

import com.brewster.poker.utility.Utils;

import java.util.ArrayList;
import java.util.List;

public class DeckBuilder {
    private static final String[] SUITS = { "Clubs", "Hearts", "Spades", "Diamonds" };
    private static final String[] NAMES = { "Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King" };
    private static final int[] VALUES = { 14, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
    private static final String[] IMAGE_PREFIXES = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
    private List<Card> cards;

    private DeckBuilder(){
        cards = buildStandardDeck();
    }


    public static DeckBuilder aDeck(){
        return new DeckBuilder();
    }

    public List<Card> build(){
        Utils.shuffleCards(cards);
        return cards;
    }

    public List<Card> buildWithoutShuffle(){
        return cards;
    }

    public DeckBuilder withJokers() {
        Card joker = new Card("any", 20, "Joker", "purple_back");
        cards.add(joker);
        cards.add(joker);
        return this;
    }


    private List<Card> buildStandardDeck(){
        List<Card> cards = new ArrayList<>();
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
