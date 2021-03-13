package com.brewster.poker.game;

import com.brewster.poker.card.Card;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokerHandTest {
    static List<Integer> highCard;
    static List<Integer> onePair;
    static List<Integer> twoPair;
    static List<Integer> threeKind;
    static List<Integer> fullHouse;
    static List<Integer> fourKind;
    static List<Integer> straight;
    static List<Card> flush = HandMother.buildStraightFlush();
    static List<Card> fullHouseCards = HandMother.buildFullHouse();
    List<Card> fourOfAKind = HandMother.buildFourOfKind();


    @BeforeAll
    static void setup(){
        highCard = List.of(4, 8, 9, 10, 11);
        onePair = List.of(4, 8, 9, 11, 11);
        twoPair = List.of(4, 4, 9, 11, 11);
        threeKind = List.of(4, 4, 4, 7, 11);
        fullHouse = List.of(4, 4, 11, 11, 11);
        fourKind = List.of(4, 11, 11, 11, 11);
        straight = List.of(4, 5, 6, 7, 8);
    }

    @Test
    void lookupScoreForHandWithHighCard() {
        //assertEquals(PokerHand.HIGH_CARD, PokerHand.lookupHand(highCard));
    }

    @Test
    void isStraight() {
        assertEquals(true, PokerHand.isStraight(straight));
    }
    @Test
    void isStraightFalse() {
        assertFalse(PokerHand.isStraight(twoPair));
    }

    @Test
    void isFlush() {
        assertTrue(PokerHand.isFlush(flush));
    }
    @Test
    void isFlushFalse() {
        assertFalse(PokerHand.isFlush(fullHouseCards));
    }

    @Test
    void isPair() {
        assertFalse(PokerHand.isPair(highCard));
        assertTrue(PokerHand.isPair(onePair));
        assertTrue(PokerHand.isPair(twoPair));
    }
    @Test
    void integerIdentity() {
        assertTrue(twoPair.get(0) == twoPair.get(1));
    }

    @Test
    void isMoreThanAPairWithPair() {
        assertEquals(PokerHand.PAIR, PokerHand.isMoreThanAPair(onePair));
    }
    @Test
    void isMoreThanAPairWithTwoPair() {
        assertEquals(PokerHand.TWO_PAIR, PokerHand.isMoreThanAPair(twoPair));
    }
    @Test
    void isMoreThanAPairWithThreeOfKind() {
        assertEquals(PokerHand.THREE_KIND, PokerHand.isMoreThanAPair(threeKind));
    }
    @Test
    void isMoreThanAPairWithFullHouse() {
        assertEquals(PokerHand.FULL_HOUSE, PokerHand.isMoreThanAPair(fullHouse));
    }
    @Test
    void isMoreThanAPairWithFourKind() {
        assertEquals(PokerHand.FOUR_KIND, PokerHand.isMoreThanAPair(fourKind));
    }

    @Test
    void HandMotherReturnsCorrectHands(){
        flush = HandMother.buildStraightFlush();
        List<Card> fourOfAKind = HandMother.buildFourOfKind();
        for (Card i : flush){
            i.show();
        }
        System.out.println();
        for (Card i : fourOfAKind){
            i.show();
        }
        System.out.println();
        for (Card i : fullHouseCards){
            i.show();
        }

    }

//    static List<Card> createHand(boolean isFlush){
//        List<Card> hand = new ArrayList<>();
//        for (int i = 0; i < 5; i++){
//            Card card = new Card("Clubs", i, "name", "A");
//            hand.add(card);
//        }
//        if (!isFlush){
//            hand.remove(0);
//            Card card = new Card("Diamonds", 8, "name", "A");
//            hand.add(card);
//        }
//        return hand;
//    }
}