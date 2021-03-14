package com.brewster.poker.game;

import com.brewster.poker.card.Card;

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
        List<Integer> cardValues = hand.stream().map(v -> v.getValue()).sorted().collect(Collectors.toList());
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

    public static boolean isStraight(List<Integer> sortedCardValues){
        int start = sortedCardValues.get(0);
        for (Integer i : sortedCardValues){
            if (start != i){
                return false;
            }
            start++;
        }
        return true;
        //FIXME need to account for Ace
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

    public static PokerHand returnPairCombos(List<Integer> sortedCardValues){
        Map<Integer, Integer> cardCount = new HashMap<>();
        for (Integer cardValue : sortedCardValues){
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

    public int getScore() {
        return score;
    }

    public String getHandName() {
        return handName;
    }
}
