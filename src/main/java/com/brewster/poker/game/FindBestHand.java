package com.brewster.poker.game;

import com.brewster.poker.cards.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FindBestHand {
    Map<String, Integer> suitCount;
    int chaseCards;
    List<Card> hand;
    int strength = 0;

    public FindBestHand(List<Card> holeCards, List<Card> riverCards){
        chaseCards = 5 - riverCards.size();
        hand = Stream.concat(holeCards.stream(), riverCards.stream())
                .sorted((a, b) -> a.getValue() - b.getValue()).collect(Collectors.toList());
        suitCount = getSuitCount(hand);
    }

    public int findBestHand(){
        int score;
        if (chaseCards == 2){
            score = flushCount();
        }
    }

    public Map<String, Integer> getSuitCount(List<Card> hand){
        suitCount = new HashMap<>();
        for (Card card : hand){
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }
        return suitCount;
    }

    public int pairCount(){

    }

    public int flushCount(){
        int count = suitCount.values().stream().mapToInt(v -> v).max().orElse(1);
        if (count == 5){
            return 8;
        } else if (count == 4){
            if (chaseCards == 1){
                return 2;
            } else if (chaseCards == 2){
                return 4;
            }
        }
        return 0;
    }

    public int straightCount(){
        List<Integer> cardValues = new ArrayList<>();
        for (int i = hand.size() - 1; i > 0; i++){
            if (hand.get(i).getValue() == 14){
                cardValues.add(1);
            } else {
                break;
            }
        }
        int count;
        hand.stream().forEach(v -> cardValues.add(v.getValue()));
        if (chaseCards > 0){
            for (int i = 0; i < cardValues.size() - 5; i++){
                boolean isMissing = false;
                int straightCount = 1;
                for (int j = i + 1; j < i + 5; j++){
                    if (cardValues.get(j -1) + 1 == cardValues.get(j)){
                        straightCount++;
                    } else {
                        if (isMissing){
                            if (straightCount >3){
                                count = straightCount;
                            }
                            break;
                        } else {
                            isMissing = true;
                        }

                    }
                }
            }
        } else {
            for (int i = 0; i < cardValues.size() - 5; i++){
                int[] fiveCardValues = new int[5];
                for (int j = 0; j < 5; j++){
                    fiveCardValues[j] = cardValues.get(i + j);
                }
                if (PokerHand.isStraight(fiveCardValues)){
                    return 7;
                }
            }
        }
        return 0;
    }

    public int chaseStrength(){
        return 1;
    }


//    public boolean isFlush(List<Card> hand){
//        for (Card card : hand){
//            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
//        }
//        return suitCount.values().stream().map(v -> v > 4).findFirst().orElse(false);
//    }



}
