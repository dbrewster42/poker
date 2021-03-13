package com.brewster.poker.game;

import com.brewster.poker.card.Card;
import com.brewster.poker.card.DeckBuilder;

import java.util.ArrayList;
import java.util.List;

public class HandMother {
    private static List<Card> cards = new DeckBuilder().buildStandardDeck();

    public static List<Card> buildFourOfKind(){
        List<Card> hand = new ArrayList<>();
        for (Card card : cards){
            hand.add(card);
            if (hand.size() == 5){
                break;
            }
        }
        return hand;
    }
    public static List<Card> buildFullHouse(){
        List<Card> hand = new ArrayList<>();
        int count = 0;
        for (Card card : cards){
            if (count == 0){
                count++;
                continue;
            }
            hand.add(card);
            if (hand.size() == 5){
                break;
            }
        }
        return hand;
    }

    public static List<Card> buildStraightFlush(){
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < 20; i+=4){
            hand.add(cards.get(i));
        }
        return hand;
    }

    public static List<Card> buildFlush(){
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < 40; i+=8){
            hand.add(cards.get(i));
        }
        return hand;
    }
}
