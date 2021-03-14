package com.brewster.poker.game;

import com.brewster.poker.card.Card;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
    static List<Card> flush = HandMother.buildFlush();
    static List<Card> straightFlush = HandMother.buildStraightFlush();
    static List<Card> fullHouseCards = HandMother.buildFullHouse();
    List<Card> fourOfAKind = HandMother.buildFourOfKind();
    List<Card> highCardCards = HandMother.buildHighCard();
    List<Card> straightCards = HandMother.buildStraight();
    List<Card> pairCards = HandMother.buildPair();
    List<Card> twoPairCards = HandMother.buildTwoPair();
    List<Card> threeOfAKind = HandMother.buildThreeOfAKind();

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
        assertEquals(PokerHand.HIGH_CARD, PokerHand.lookupHand(highCardCards));
    }
    @Test
    void lookupScoreForHandWithPair() {
        assertEquals(PokerHand.HIGH_CARD, PokerHand.lookupHand(highCardCards));
    }
    @Test
    void lookupScoreForHandWithTwoPair() {
        assertEquals(PokerHand.PAIR, PokerHand.lookupHand(pairCards));
    }
    @Test
    void lookupScoreForHandWithThreeKind() {
        assertEquals(PokerHand.THREE_KIND, PokerHand.lookupHand(threeOfAKind));
    }
    @Test
    void lookupScoreForHandWithStraight() {
        assertEquals(PokerHand.STRAIGHT, PokerHand.lookupHand(straightCards));
    }
    @Test
    void lookupScoreForHandWithFlush() {
        assertEquals(PokerHand.FLUSH, PokerHand.lookupHand(flush));
    }
    @Test
    void lookupScoreForHandWithFullHouse() {
        assertEquals(PokerHand.FULL_HOUSE, PokerHand.lookupHand(fullHouseCards));
    }
    @Test
    void lookupScoreForHandWithFourKind() {
        assertEquals(PokerHand.FOUR_KIND, PokerHand.lookupHand(fourOfAKind));
    }
    @Test
    void lookupScoreForHandWithStraightFlush() {
        assertEquals(PokerHand.STRAIGHT_FLUSH, PokerHand.lookupHand(straightFlush));
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
    void integerIdentity() {
        assertTrue(twoPair.get(0) == twoPair.get(1));
    }

    @Test
    void isMoreThanAPairWithPair() {
        assertEquals(PokerHand.PAIR, PokerHand.returnPairCombos(onePair));
    }
    @Test
    void isMoreThanAPairWithTwoPair() {
        assertEquals(PokerHand.TWO_PAIR, PokerHand.returnPairCombos(twoPair));
    }
    @Test
    void isMoreThanAPairWithThreeOfKind() {
        assertEquals(PokerHand.THREE_KIND, PokerHand.returnPairCombos(threeKind));
    }
    @Test
    void isMoreThanAPairWithFullHouse() {
        assertEquals(PokerHand.FULL_HOUSE, PokerHand.returnPairCombos(fullHouse));
    }
    @Test
    void isMoreThanAPairWithFourKind() {
        assertEquals(PokerHand.FOUR_KIND, PokerHand.returnPairCombos(fourKind));
    }

    @Test
    void HandMotherReturnsCorrectHandSize(){
        assertEquals(flush.size(), 5);
        assertEquals(straightFlush.size(), 5);
        assertEquals(highCardCards.size(), 5);
        assertEquals(fourOfAKind.size(), 5);
        assertEquals(fullHouseCards.size(), 5);
        assertEquals(twoPairCards.size(), 5);
    }

}