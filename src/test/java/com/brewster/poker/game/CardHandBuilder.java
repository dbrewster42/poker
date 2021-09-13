package com.brewster.poker.game;

import com.brewster.poker.card.Card;
import com.brewster.poker.card.DeckBuilder;

import java.util.ArrayList;
import java.util.List;

public class CardHandBuilder {
    private static List<Card> cards = new DeckBuilder().buildStandardDeck();

    public static int[] buildHighAceStraightValues(){
        return new int[]{10, 11, 12, 13, 14};

    }
    public static int[] buildLowAceStraightValues(){
        return new int[]{2, 3, 4, 5, 14};
    }
    public static int[] buildAlmostStraightValues(){
        return new int[]{2, 3, 4, 13, 14};
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
    public static List<Card> buildFourOfKindWithSevenCards(){
        List<Card> hand = buildFourOfKind();
        hand.add(cards.get(17));
        hand.add(cards.get(23));
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
    public static List<Card> buildFullHouseWithSeven(){
        List<Card> hand = buildFullHouse();
        hand.add(cards.get(33));
        hand.add(cards.get(39));
        return hand;
    }

    public static List<Card> buildStraightFlush(){
        List<Card> hand = new ArrayList<>();
        for (int i = 4; i < 24; i+=4){
            hand.add(cards.get(i));
        }
        return hand;
    }
    public static List<Card> buildStraightFlushWithSeven(){
        List<Card> hand = buildStraightFlush();
        hand.add(cards.get(1));
        hand.add(cards.get(44));
        return hand;
    }

    public static List<Card> buildFlush(){
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < 40; i+=8){
            hand.add(cards.get(i));
        }
        return hand;
    }
    public static List<Card> buildFlushWithSeven(){
        List<Card> hand = buildFlush();
        hand.add(cards.get(1));
        hand.add(cards.get(2));
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
    public static List<Card> buildPairWithSeven(){
        List<Card> hand = buildPair();
        hand.add(cards.get(39));
        hand.add(cards.get(44));
        return hand;
    }
    public static List<Card> buildStraight(){
        List<Card> hand = new ArrayList<>();
        for (int i = 4; i < 17; i+=4){
            hand.add(cards.get(i));
        }
        hand.add(cards.get(22));
        return hand;
    }
    public static List<Card> buildStraightWithSevenCards2() {
        List<Card> straightCards2 = new ArrayList<>();
        straightCards2.add(cards.get(0));
        straightCards2.add(cards.get(21));
        straightCards2.add(cards.get(38));
        straightCards2.add(cards.get(41));
        straightCards2.add(cards.get(44));
        straightCards2.add(cards.get(49));
        straightCards2.add(cards.get(50));
        return straightCards2;
    }
        public static List<Card> buildStraightWithSevenCards(){
        List<Card> hand = buildStraight();
        hand.add(cards.get(33));
        hand.add(cards.get(45));
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
    public static List<Card> buildThreeOfAKindWithSeven(){
        List<Card> hand = buildThreeOfAKind();
        hand.add(cards.get(24));
        hand.add(cards.get(34));
        return hand;
    }
    public static List<Card> buildTwoPair(){
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < 10; i+=2){
            hand.add(cards.get(i));
        }
        return hand;
    }
    public static List<Card> buildTwoPairWithSevenCards(){
        List<Card> hand = buildTwoPair();
        hand.add(cards.get(24));
        hand.add(cards.get(34));

        return hand;
    }
}
