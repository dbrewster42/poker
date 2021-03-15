package com.brewster.poker.game;

import com.brewster.poker.card.Card;
import com.brewster.poker.card.DeckBuilder;

import java.util.ArrayList;
import java.util.List;

public class HandMother {
    private static List<Card> cards = new DeckBuilder().buildStandardDeck();

    public static List<Integer> buildHighAceStraightValues(){
        List<Integer> aceStraight = new ArrayList<>();
        aceStraight.add(10);
        aceStraight.add(11);
        aceStraight.add(12);
        aceStraight.add(13);
        aceStraight.add(14);
        return aceStraight;
    }
    public static List<Integer> buildLowAceStraightValues(){
        List<Integer> aceStraight = new ArrayList<>();
        aceStraight.add(2);
        aceStraight.add(3);
        aceStraight.add(4);
        aceStraight.add(5);
        aceStraight.add(14);
        return aceStraight;
    }
    public static List<Integer> buildAlmostStraightValues(){
        List<Integer> aceStraight = new ArrayList<>();
        aceStraight.add(2);
        aceStraight.add(3);
        aceStraight.add(4);
        aceStraight.add(13);
        aceStraight.add(14);
        return aceStraight;
    }

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

    public static List<Card> buildHighCard(){
        List<Card> hand = new ArrayList<>();
        for (int i = 4; i < 36; i+=7){
            hand.add(cards.get(i));
        }
        return hand;
    }
    public static List<Card> buildPair(){
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < 34; i+=11){
            hand.add(cards.get(i));
        }
        hand.add(cards.get(1));
        return hand;
    }
    public static List<Card> buildStraight(){
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < 13; i+=4){
            hand.add(cards.get(i));
        }
        hand.add(cards.get(18));
        return hand;
    }
    public static List<Card> buildThreeOfAKind(){
        List<Card> hand = new ArrayList<>();
        for (int i = 3; i < 9; i++){
            if (i == 4){
                continue;
            }
            hand.add(cards.get(i));
        }
        return hand;
    }
    public static List<Card> buildTwoPair(){
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < 10; i+=2){
            hand.add(cards.get(i));
        }
        return hand;
    }
}
