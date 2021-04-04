package com.brewster.poker.game;

import com.brewster.poker.cards.Card;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokerHandTest {
    int[] lowAceStraight = HandMother.buildLowAceStraightValues();
    int[] highAceStraight = HandMother.buildHighAceStraightValues();
    int[] almostStraight = HandMother.buildAlmostStraightValues();
    static List<Card> flush = HandMother.buildFlush();
    static List<Card> straightFlush = HandMother.buildStraightFlush();
    static List<Card> fullHouseCards = HandMother.buildFullHouse();
    List<Card> fourOfAKind = HandMother.buildFourOfKind();
    List<Card> highCardCards = HandMother.buildHighCard();
    List<Card> straightCards = HandMother.buildStraight();
    List<Card> pairCards = HandMother.buildPair();
    List<Card> twoPairCards = HandMother.buildTwoPair();
    List<Card> threeOfAKind = HandMother.buildThreeOfAKind();
    int[] highCard = new int[]{3, 5, 9, 12, 14};
    int[] onePair = new int[]{4, 8, 9, 11, 11};
    int[] twoPair = new int[]{4, 4, 9, 11, 11};
    int[] threeKind = new int[]{4, 4, 4, 7, 11};
    int[] fullHouse = new int[]{4, 4, 11, 11, 11};
    int[] fourKind = new int[]{4, 11, 11, 11, 11};
    int[] straight = new int[]{4, 5, 6, 7, 8};


    @Test
    void lookupHandWithHighCard() {
        assertEquals(PokerHand.HIGH_CARD, PokerHand.lookupHand(highCardCards));
    }
    @Test
    void lookupHandWithPair() {
        assertEquals(PokerHand.PAIR, PokerHand.lookupHand(pairCards));
    }
    @Test
    void lookupHandWithTwoPair() {
        assertEquals(PokerHand.TWO_PAIR, PokerHand.lookupHand(twoPairCards));
    }
    @Test
    void lookupHandWithThreeKind() {
        assertEquals(PokerHand.THREE_KIND, PokerHand.lookupHand(threeOfAKind));
    }
    @Test
    void lookupHandWithStraight() {
        assertEquals(PokerHand.STRAIGHT, PokerHand.lookupHand(straightCards));
    }
    @Test
    void lookupHandWithFlush() {
        assertEquals(PokerHand.FLUSH, PokerHand.lookupHand(flush));
    }
    @Test
    void lookupHandWithFullHouse() {
        assertEquals(PokerHand.FULL_HOUSE, PokerHand.lookupHand(fullHouseCards));
    }
    @Test
    void lookupHandWithFourKind() {
        assertEquals(PokerHand.FOUR_KIND, PokerHand.lookupHand(fourOfAKind));
    }
    @Test
    void lookupHandWithStraightFlush() {
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
    void isStraightWithLowAce() {
        assertEquals(true, PokerHand.isStraight(lowAceStraight));
    }
    @Test
    void isStraightWithHighAce() {
        assertEquals(true, PokerHand.isStraight(highAceStraight));
    }
    @Test
    void isAlmostStraight() {
        assertEquals(false, PokerHand.isStraight(almostStraight));
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
    void returnPairCombosWithHighCard() {
        assertEquals(PokerHand.HIGH_CARD, PokerHand.returnPairCombos(highCard));
    }
    @Test
    void returnPairCombosWithPair() {
        assertEquals(PokerHand.PAIR, PokerHand.returnPairCombos(onePair));
    }
    @Test
    void returnPairCombosWithTwoPair() {
        assertEquals(PokerHand.TWO_PAIR, PokerHand.returnPairCombos(twoPair));
    }
    @Test
    void returnPairCombosWithThreeOfKind() {
        assertEquals(PokerHand.THREE_KIND, PokerHand.returnPairCombos(threeKind));
    }
    @Test
    void returnPairCombosWithFullHouse() {
        assertEquals(PokerHand.FULL_HOUSE, PokerHand.returnPairCombos(fullHouse));
    }
    @Test
    void returnPairCombosWithFourKind() {
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