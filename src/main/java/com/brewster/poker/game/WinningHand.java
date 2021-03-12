package com.brewster.poker.game;

import com.brewster.poker.card.Card;

import java.util.List;

public enum WinningHand {

    ROYAL_FLUSH(12, "Royal Flush"),
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

    WinningHand(int score, String handName){
        this.score = score;
        this.handName = handName;
    }

    public static WinningHand lookupScoreForHand(List<Card> hand){

    }

    public static boolean isStraight(){
        return false;
    }
    public static boolean isFlush(){
        return false;
    }
    public static boolean isPair(){
        return false;
    }

    public int getScore() {
        return score;
    }

    public String getHandName() {
        return handName;
    }
}
