package com.brewster.poker.game;

import com.brewster.poker.card.Card;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PokerHandSevenCardsTest {
     List<Card> fourOfAKind = CardHandBuilder.buildFourOfKindWithSevenCards();
//     List<Card> highCardCards = CardHandBuilder.buildHighCard();
     List<Card> straightCards = CardHandBuilder.buildStraightWithSevenCards();
     List<Card> pairCards = CardHandBuilder.buildPairWithSeven();
     List<Card> twoPairCards = CardHandBuilder.buildTwoPairWithSevenCards();
     List<Card> threeOfAKind = CardHandBuilder.buildThreeOfAKindWithSeven();
     List<Card> flush = CardHandBuilder.buildFlushWithSeven();
     List<Card> straightFlush = CardHandBuilder.buildStraightFlushWithSeven();
     List<Card> fullHouseCards = CardHandBuilder.buildFullHouseWithSeven();

//     @Test
//     void lookupHandWithHighCard() {
//          assertEquals(PokerHand.HIGH_CARD, PokerHand.lookupHand(highCardCards));
//     }
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
     void lookupHandWithStraight2() {
          List<Card> straightCards2 = CardHandBuilder.buildStraightWithSevenCards2();

          assertEquals(PokerHand.STRAIGHT, PokerHand.lookupHand(straightCards2));
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
}
