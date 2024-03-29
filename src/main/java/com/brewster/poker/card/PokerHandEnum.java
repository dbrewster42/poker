package com.brewster.poker.card;

import java.util.List;


public enum PokerHandEnum {

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

    PokerHandEnum(int score, String handName){
        this.score = score;
        this.handName = handName;
    }

    public static PokerHandEnum lookupHand(List<Card> hand){
        return PokerHandLookup.lookupHand(hand);
    }

    public int getScore() {
        return score;
    }
    public String getHandName() {
        return handName;
    }
}

//    public static boolean isStraight(int[] sortedCardValues){
//        int start = sortedCardValues[0];
//        if (start == 2 && sortedCardValues[4] == 14){
//            for (int i = 4; i > 0; i--){
//                sortedCardValues[i] = sortedCardValues[i - 1];
//            }
//            sortedCardValues[0] = 1;
//            start = 1;
//        }
//        for (int i : sortedCardValues){
//            if (start != i){
//                return false;
//            }
//            start++;
//        }
//        return true;
//    }
//    public static boolean isFlush(List<Card> hand){
//        for (String suit : DeckBuilder.getSUITS()){
//            int count = 0;
//            for (Card card : hand){
//                if (card.getSuit().equals(suit)){
//                    count++;
//                }
//            }
//            if (count == 5){
//                highCardValue = hand.stream().mapToInt(v -> v.getValue()).max().orElse(0);
//                return true;
//            }
//        }
//        return false;
////        String suit = hand.get(0).getSuit();
////        for (Card card : hand){
////            if (!card.getSuit().equals(suit)){
////                return false;
////            }
////        }
////        return true;
//    }

