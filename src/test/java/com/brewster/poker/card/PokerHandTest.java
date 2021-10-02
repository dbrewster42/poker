package com.brewster.poker.card;

import com.brewster.poker.CardHandBuilder;
import com.brewster.poker.TestDataBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokerHandTest {
    int[] lowAceStraight = CardHandBuilder.buildLowAceStraightValues();
    int[] sevenCardLowAceStraight = CardHandBuilder.buildLowAceStraightWithSevenCards();
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
    void tieBreaker(){
        assertEquals(2, PokerHandLookup.getTieBreaker(TestDataBuilder.getPlayer(), TestDataBuilder.getPlayer()).size());
    }


    @Test
    void lookupHandWithHighCard() {
        assertEquals(PokerHandEnum.HIGH_CARD, PokerHandEnum.lookupHand(highCardCards));
    }
    @Test
    void lookupHandWithPair() {
        assertEquals(PokerHandEnum.PAIR, PokerHandEnum.lookupHand(pairCards));
    }
    @Test
    void lookupHandWithTwoPair() {
        assertEquals(PokerHandEnum.TWO_PAIR, PokerHandEnum.lookupHand(twoPairCards));
    }
    @Test
    void lookupHandWithThreeKind() {
        assertEquals(PokerHandEnum.THREE_KIND, PokerHandEnum.lookupHand(threeOfAKind));
    }
    @Test
    void lookupHandWithStraight() {
        assertEquals(PokerHandEnum.STRAIGHT, PokerHandEnum.lookupHand(straightCards));
    }
    @Test
    void lookupHandWithFlush() {
        assertEquals(PokerHandEnum.FLUSH, PokerHandEnum.lookupHand(flush));
    }
    @Test
    void lookupHandWithFullHouse() {
        assertEquals(PokerHandEnum.FULL_HOUSE, PokerHandEnum.lookupHand(fullHouseCards));
    }
    @Test
    void lookupHandWithFourKind() {
        assertEquals(PokerHandEnum.FOUR_KIND, PokerHandEnum.lookupHand(fourOfAKind));
    }
    @Test
    void lookupHandWithStraightFlush() {
        assertEquals(PokerHandEnum.STRAIGHT_FLUSH, PokerHandEnum.lookupHand(straightFlush));
    }

    @Test
    void isStraight() {
        assertEquals(true, PokerHandLookup.isStraight(straight));
    }
    @Test
    void isStraightFalse() {
        assertFalse(PokerHandLookup.isStraight(twoPair));
    }
    @Test
    void isStraightWithLowAce() {
        assertEquals(true, PokerHandLookup.isStraight(lowAceStraight));
    }
    @Test
    void isStraightWithLowAce2() {
        assertEquals(true, PokerHandLookup.isStraight(sevenCardLowAceStraight));
    }
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
        assertEquals(PokerHandEnum.HIGH_CARD, PokerHandLookup.returnPairCombos(highCard));
    }
    @Test
    void returnPairCombosWithPair() {
        assertEquals(PokerHandEnum.PAIR, PokerHandLookup.returnPairCombos(onePair));
    }
    @Test
    void returnPairCombosWithTwoPair() {
        assertEquals(PokerHandEnum.TWO_PAIR, PokerHandLookup.returnPairCombos(twoPair));
    }
    @Test
    void returnPairCombosWithThreeOfKind() {
        assertEquals(PokerHandEnum.THREE_KIND, PokerHandLookup.returnPairCombos(threeKind));
    }
    @Test
    void returnPairCombosWithFullHouse() {
        assertEquals(PokerHandEnum.FULL_HOUSE, PokerHandLookup.returnPairCombos(fullHouse));
    }
    @Test
    void returnPairCombosWithFourKind() {
        assertEquals(PokerHandEnum.FOUR_KIND, PokerHandLookup.returnPairCombos(fourKind));
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