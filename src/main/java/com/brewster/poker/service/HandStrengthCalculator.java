package com.brewster.poker.service;

import com.brewster.poker.card.Card;
import com.brewster.poker.card.PokerHandLookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HandStrengthCalculator {
    private Map<String, Integer> suitMap;
    private int chaseCards;
    private List<Card> hand;
    private int strength = 0;

    public HandStrengthCalculator(List<Card> computerHand){
        chaseCards = 7 - computerHand.size();
        hand = computerHand;
//        hand = Stream.concat(holeCards.stream(), riverCards.stream())
//                .sorted((a, b) -> a.getValue() - b.getValue()).collect(Collectors.toList());
        suitMap = getSuitCount();
        strength = findHandStrength();
    }

    public static int lookupHoleCards(List<Card> hand){
        if (hand.get(0).getValue() == hand.get(1).getValue()){
            if (hand.get(0).getValue() > 8){
                return 7;
            }
            return 6;
        }
        if (hand.get(0).getValue() > 10 || hand.get(1).getValue() > 10){
            return 4;
        }
        return 2;
    }

    public int findHandStrength(){
        int score = getHigherScore(flushCount(), straightCount());
        return getHigherScore(score, pairCount());
    }

    public int getHigherScore(int score, int score2){
        if (score < score2){
            score = score2;
        }
        return score;
    }

    public Map<String, Integer> getSuitCount(){
        Map<String, Integer> suitMap = new HashMap<>();
        for (Card card : hand){
            suitMap.put(card.getSuit(), suitMap.getOrDefault(card.getSuit(), 0) + 1);
        }
        return suitMap;
    }

    public int pairCount(){
        int score = 0;
        List<Integer> cardValues = hand.stream().map(Card::getValue).collect(Collectors.toList());
        Map<Integer, Integer> cardCount = new HashMap<>();
        for (int cardValue : cardValues){
            cardCount.put(cardValue, cardCount.getOrDefault(cardValue, 0) + 1);
        }
        List<Integer> counts = cardCount.values().stream().filter(v -> v > 1)
                .sorted().collect(Collectors.toList());
        if (!counts.isEmpty()){
            int highestNumberOfCards = counts.get(counts.size()-1);
            score = (int) Math.pow(2, highestNumberOfCards);
            if (counts.size() > 1){
                score *= 2;
            }
            //TODO more elegant solution needed to account for chase cards
            if (chaseCards == 1){
                score += 2;
            } else if (chaseCards == 2){
                score += 4;
            }
        }
        return score;
    }

    public int flushCount(){
        int score = 0;
        int count = suitMap.values().stream().mapToInt(v -> v).max()
                .orElseThrow(() -> new RuntimeException("computer could not find card suits"));
        if (count == 5){
            score = 12;
        } else if (count == 4){
            if (chaseCards == 1){
                score = 3;
            } else if (chaseCards == 2){
                score = 6;
            }
        }
        return score;
    }

    public int straightCount(){ //TODO does this function work?
        List<Integer> cardValues = new ArrayList<>();
        for (int i = hand.size() - 1; i > 0; i--){
            if (hand.get(i).getValue() == 14){
                cardValues.add(1);
            } else {
                break;
            }
        }
        int score = 0;
        int count = 0;
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
                            break;
                        } else {
                            isMissing = true;
                        }

                    }
                }
                if (straightCount > 3){
                    count = straightCount;
                }
            }
            if (count == 5){
                score = 10;
            } else if (count == 4 && chaseCards > 1){
                score = 4;
            }
        } else {
            for (int i = 0; i < cardValues.size() - 5; i++){
                int[] fiveCardValues = new int[5];
                for (int j = 0; j < 5; j++){
                    fiveCardValues[j] = cardValues.get(i + j);
                }
                if (PokerHandLookup.isStraight(fiveCardValues)){
                    score = 10;
                }
            }
        }
        return score;
    }

    public int getStrength() {
        return strength;
    }
}
