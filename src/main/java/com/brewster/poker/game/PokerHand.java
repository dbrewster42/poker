package com.brewster.poker.game;

import com.brewster.poker.cards.Card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum PokerHand {

    ROYAL_FLUSH(12, "Royal Flush"),
    FIVE_KIND(11, "Five of a Kind"),
    STRAIGHT_FLUSH(10, "Straight Flush"),
    FOUR_KIND(9, "Four of a Kind"),
    FULL_HOUSE(8, "Full House"),
    FLUSH(7, "Flush"),
    STRAIGHT(6, "Straight"),
    THREE_KIND(5, "Three of a Kind"),
    TWO_PAIR(4, "Two Pairs"),
    PAIR(3, "Pair"),
    HIGH_CARD(2, "High Card");

    private final int score;
    private final String handName;

    PokerHand(int score, String handName){
        this.score = score;
        this.handName = handName;
    }

    public static PokerHand lookupHand(List<Card> hand){
        int[] cardValues = hand.stream().mapToInt(Card::getValue).sorted().toArray();
        PokerHand pokerHand = returnPairCombos(cardValues);
        if (pokerHand.getScore() > 2){
            return pokerHand;
        }
        if (isFlush(hand)){
            if (isStraight(cardValues)){
                pokerHand = STRAIGHT_FLUSH;
            } else {
                pokerHand = FLUSH;
            }
        } else if (isStraight(cardValues)){
            pokerHand = STRAIGHT;
        }

        return pokerHand;
    }
    public static int lookupHoleCards(List<Card> hand){
        if (hand.get(0).getValue() == hand.get(1).getValue()){
            if (hand.get(0).getValue() > 8){
                return 5;
            }
            return 4;
        }
        if (hand.get(0).getValue() > 10 || hand.get(1).getValue() > 10){
            return 2;
        }
        return 0;
    }

    public static PokerHand returnPairCombos(int[] sortedCardValues){
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

    public static boolean isFlush(List<Card> hand){
        String suit = hand.get(0).getSuit();
        for (Card card : hand){
            if (!card.getSuit().equals(suit)){
                return false;
            }
        }
        return true;
    }

    public static boolean isStraight(int[] sortedCardValues){
        int start = sortedCardValues[0];
        if (start == 2 && sortedCardValues[4] == 14){
            for (int i = 4; i > 0; i--){
                sortedCardValues[i] = sortedCardValues[i - 1];
            }
            sortedCardValues[0] = 1;
            start = 1;
        }
        for (int i : sortedCardValues){
            if (start != i){
                return false;
            }
            start++;
        }
        return true;
    }

    public int getScore() {
        return score;
    }
    public String getHandName() {
        return handName;
    }
}
