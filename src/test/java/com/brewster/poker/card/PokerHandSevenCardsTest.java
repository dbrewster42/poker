package com.brewster.poker.card;

import com.brewster.poker.CardHandBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PokerHandSevenCardsTest {
     List<Card> fourOfAKind = CardHandBuilder.buildFourOfKindWithSevenCards();
     List<Card> pairCards = CardHandBuilder.buildPairWithSeven();
     List<Card> twoPairCards = CardHandBuilder.buildTwoPairWithSevenCards();
     List<Card> threeOfAKind = CardHandBuilder.buildThreeOfAKindWithSeven();
     List<Card> flush = CardHandBuilder.buildFlushWithSeven();
     List<Card> straightFlush = CardHandBuilder.buildStraightFlushWithSeven();
     List<Card> fullHouseCards = CardHandBuilder.buildFullHouseWithSeven();
     List<Card> straightCards = CardHandBuilder.buildStraightWithSevenCards();
     List<Card> highCardCards = CardHandBuilder.buildHighCard();

     @Test
     void lookupHandWithHighCard() {
          assertEquals(PokerHandEnum.HIGH_CARD, PokerHandLookup.lookupHand(highCardCards));
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
     void lookupHandWithStraight2() {
          List<Card> straightCards2 = CardHandBuilder.buildStraightWithSevenCards2();

          assertEquals(PokerHandEnum.STRAIGHT, PokerHandEnum.lookupHand(straightCards2));
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
}
