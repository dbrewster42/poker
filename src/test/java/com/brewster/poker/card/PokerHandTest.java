package com.brewster.poker.card;

import com.brewster.poker.CardHandBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokerHandTest {
    int[] lowAceStraight = CardHandBuilder.buildLowAceStraightValues();
    int[] highAceStraight = CardHandBuilder.buildHighAceStraightValues();
    int[] almostStraight = CardHandBuilder.buildAlmostStraightValues();
    static List<Card> flush = CardHandBuilder.buildFlush();
    static List<Card> straightFlush = CardHandBuilder.buildStraightFlush();
    static List<Card> fullHouseCards = CardHandBuilder.buildFullHouse();
    List<Card> fourOfAKind = CardHandBuilder.buildFourOfKind();
    List<Card> highCardCards = CardHandBuilder.buildHighCard();
    List<Card> straightCards = CardHandBuilder.buildStraight();
    List<Card> pairCards = CardHandBuilder.buildPair();
    List<Card> twoPairCards = CardHandBuilder.buildTwoPair();
    List<Card> threeOfAKind = CardHandBuilder.buildThreeOfAKind();
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
        assertEquals(true, PokerHandLookup.isStraight(straight));
    }
    @Test
    void isStraightFalse() {
        assertFalse(PokerHandLookup.isStraight(twoPair));
    }
    //FIXME need to account for low ace
//    @Test
//    void isStraightWithLowAce() {
//        assertEquals(true, PokerHand.isStraight(lowAceStraight));
//    }
    @Test
    void isStraightWithHighAce() {
        assertEquals(true, PokerHandLookup.isStraight(highAceStraight));
    }
    @Test
    void isAlmostStraight() {
        assertEquals(false, PokerHandLookup.isStraight(almostStraight));
    }

//    @Test
//    void isFlush() {
//        assertTrue(PokerHand.isFlush(flush));
//    }
//    @Test
//    void isFlushFalse() {
//        assertFalse(PokerHand.isFlush(fullHouseCards));
//    }

    @Test
    void returnPairCombosWithHighCard() {
        assertEquals(PokerHand.HIGH_CARD, PokerHandLookup.returnPairCombos(highCard));
    }
    @Test
    void returnPairCombosWithPair() {
        assertEquals(PokerHand.PAIR, PokerHandLookup.returnPairCombos(onePair));
    }
    @Test
    void returnPairCombosWithTwoPair() {
        assertEquals(PokerHand.TWO_PAIR, PokerHandLookup.returnPairCombos(twoPair));
    }
    @Test
    void returnPairCombosWithThreeOfKind() {
        assertEquals(PokerHand.THREE_KIND, PokerHandLookup.returnPairCombos(threeKind));
    }
    @Test
    void returnPairCombosWithFullHouse() {
        assertEquals(PokerHand.FULL_HOUSE, PokerHandLookup.returnPairCombos(fullHouse));
    }
    @Test
    void returnPairCombosWithFourKind() {
        assertEquals(PokerHand.FOUR_KIND, PokerHandLookup.returnPairCombos(fourKind));
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