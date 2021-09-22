package com.brewster.poker.card;

import com.brewster.poker.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.brewster.poker.card.PokerHand.FLUSH;
import static com.brewster.poker.card.PokerHand.FOUR_KIND;
import static com.brewster.poker.card.PokerHand.FULL_HOUSE;
import static com.brewster.poker.card.PokerHand.HIGH_CARD;
import static com.brewster.poker.card.PokerHand.PAIR;
import static com.brewster.poker.card.PokerHand.STRAIGHT;
import static com.brewster.poker.card.PokerHand.STRAIGHT_FLUSH;
import static com.brewster.poker.card.PokerHand.THREE_KIND;
import static com.brewster.poker.card.PokerHand.TWO_PAIR;

public class PokerHandLookup {
     public static PokerHand lookupHand(List<Card> hand){
          int[] cardValues = hand.stream().mapToInt(Card::getValue).sorted().toArray();
          PokerHand pokerHand = returnPairCombos(cardValues);

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

     public static List<Player> getTieBreaker(Player first, Player second){
          PokerHand pokerHand = first.getPokerHand();
          List<Card> firstHand = first.getHand().stream().sorted((a, b) -> a.getValue() - b.getValue()).collect(Collectors.toList());
          List<Card> secondHand = second.getHand().stream().sorted((a, b) -> a.getValue() - b.getValue()).collect(Collectors.toList());

          firstHand = getFiveBestCards(firstHand, pokerHand);
          //TODO do I have to do each separately?
          return List.of(first, second);
     }

     private static List<Card> getFiveBestCards(List<Card> cards, PokerHand pokerHand){
          //TODO
          return cards;
     }

     private static PokerHand getBestHand(PokerHand first, PokerHand second){
          if (first.getScore() < second.getScore()){
               first = second;
          }
          return first;
     }

     protected static PokerHand returnPairCombos(int[] sortedCardValues){
          Map<Integer, Integer> cardCount = new HashMap<>();
          for (int cardValue : sortedCardValues){
               cardCount.put(cardValue, cardCount.getOrDefault(cardValue, 0) + 1);
          }
          List<Integer> counts = cardCount.values().stream().filter(v -> v > 1).collect(Collectors.toList());

          int firstPairCount;
          int secondPairCount = 1;
          if (counts.isEmpty()){
//            highCardValue = sortedCardValues[sortedCardValues.length - 1];
               return HIGH_CARD;
          } else {
               firstPairCount = counts.get(0);
               if (counts.size() > 1){
                    secondPairCount = counts.get(1);
               }
          }

          PokerHand returnValue = PAIR;
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
                            .mapToInt(v -> v.getValue())
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
          return false;
     }
}
