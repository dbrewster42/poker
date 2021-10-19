package com.brewster.poker.card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.brewster.poker.card.PokerHandEnum.FLUSH;
import static com.brewster.poker.card.PokerHandEnum.FOUR_KIND;
import static com.brewster.poker.card.PokerHandEnum.FULL_HOUSE;
import static com.brewster.poker.card.PokerHandEnum.HIGH_CARD;
import static com.brewster.poker.card.PokerHandEnum.PAIR;
import static com.brewster.poker.card.PokerHandEnum.STRAIGHT;
import static com.brewster.poker.card.PokerHandEnum.STRAIGHT_FLUSH;
import static com.brewster.poker.card.PokerHandEnum.THREE_KIND;
import static com.brewster.poker.card.PokerHandEnum.TWO_PAIR;

public class PokerHandLookup {

     private PokerHandLookup(){}

     public static PokerHandEnum lookupHand(List<Card> hand){
          int[] cardValues = hand.stream().mapToInt(Card::getValue).sorted().toArray();
          PokerHandEnum pokerHand = returnPairCombos(cardValues);

          int isFlush = isStraightFlush(hand);
          if (isFlush == 2){
               pokerHand = STRAIGHT_FLUSH;
          } else if (isFlush == 1){
               pokerHand = getBestHand(FLUSH, pokerHand);
          } else if (isStraight(cardValues)){
               pokerHand = getBestHand(STRAIGHT, pokerHand);
          }

          return pokerHand;
     }

     private static PokerHandEnum getBestHand(PokerHandEnum first, PokerHandEnum second){
          if (first.getScore() < second.getScore()){
               first = second;
          }
          return first;
     }

     protected static PokerHandEnum returnPairCombos(int[] sortedCardValues){
          Map<Integer, Integer> cardCount = new HashMap<>();
          for (int cardValue : sortedCardValues){
               cardCount.put(cardValue, cardCount.getOrDefault(cardValue, 0) + 1);
          }
          List<Integer> counts = cardCount.values().stream().filter(v -> v > 1).collect(Collectors.toList());

          int firstPairCount;
          int secondPairCount = 1;
          if (counts.isEmpty()){
               return HIGH_CARD;
          } else {
               firstPairCount = counts.get(0);
               if (counts.size() > 1){
                    secondPairCount = counts.get(1);
               }
          }

          PokerHandEnum returnValue = PAIR;
          if (firstPairCount > 2 || secondPairCount > 2){
               if (firstPairCount > 3 || secondPairCount > 3){
                    returnValue = FOUR_KIND;
               } else if (firstPairCount + secondPairCount == 5){
                    returnValue = FULL_HOUSE;
               } else {
                    returnValue = THREE_KIND;
               }
          } else if (firstPairCount + secondPairCount == 4) {
               returnValue = TWO_PAIR;
          }
          return returnValue;
     }

     protected static int isStraightFlush(List<Card> hand){
          for (String suit : DeckBuilder.getSUITS()){
               int count = 0;
               for (Card card : hand){
                    if (card.getSuit().equals(suit)){
                         count++;
                    }
               }
               if (count >= 5){
                    int[] cards = hand.stream().filter(v -> v.getSuit().equals(suit))
                            .mapToInt(Card::getValue)
                            .sorted().toArray();
                    if (isStraight(cards)){
                         return 2;
                    }
                    return 1;
               }
          }
          return 0;

     }

     public static boolean isStraight(int[] sortedCardValues){
          int start = -1;
          int count = 1;
          for (int i : sortedCardValues){
               if (start == i){
                    count++;
               } else {
                    if (start - 1 == i){
                         continue;
                    }
                    count = 1;
                    start = i;
               }
               if (count == 5){
                    return true;
               }
               start++;
          }
          if (sortedCardValues[sortedCardValues.length - 1] == 14){
               for (int i = sortedCardValues.length - 1; i > 0; i--){
                    sortedCardValues[i] = sortedCardValues[i - 1];
               }
               sortedCardValues[0] = 1;
               return isStraight(sortedCardValues);
          }
          return false;
     }
}
