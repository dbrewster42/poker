package com.brewster.poker.card;

import com.brewster.poker.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.brewster.poker.card.PokerHandEnum.FLUSH;
import static com.brewster.poker.card.PokerHandEnum.FOUR_KIND;
import static com.brewster.poker.card.PokerHandEnum.FULL_HOUSE;
import static com.brewster.poker.card.PokerHandEnum.PAIR;
import static com.brewster.poker.card.PokerHandEnum.STRAIGHT;
import static com.brewster.poker.card.PokerHandEnum.THREE_KIND;
import static com.brewster.poker.card.PokerHandEnum.TWO_PAIR;

public class PokerHandTieBreaker {
     private Player firstPlayer;
     private Player secondPlayer;
//     private List<Card> firstHand;
//     private List<Card> secondHand;
     private PokerHandEnum pokerHand;

     public PokerHandTieBreaker(Player first, Player second){
          this.firstPlayer = first;
          this.secondPlayer = second;
          pokerHand = first.getPokerHand();
//          firstHand = first.getCards().stream().sorted((a, b) -> b.getValue() - a.getValue()).collect(Collectors.toList());
//          secondHand = second.getCards().stream().sorted((a, b) -> b.getValue() - a.getValue()).collect(Collectors.toList());
     }
     public List<Player> getTieBreaker(){
//          PokerHandEnum pokerHand = first.getPokerHand();
          List<Card> firstHand = firstPlayer.getCards().stream().sorted((a, b) -> b.getValue() - a.getValue()).collect(Collectors.toList());
          List<Card> secondHand = secondPlayer.getCards().stream().sorted((a, b) -> b.getValue() - a.getValue()).collect(Collectors.toList());

          int firstHighCard = getPrimaryHighCard(firstHand).get(0);
          int secondHighCard = getPrimaryHighCard(secondHand).get(0);
          //TODO calculate secondary
          List<Integer> firstCards = getPrimaryHighCard(firstHand);
          List<Integer> secondCards = getPrimaryHighCard(secondHand);

          List<Player> winners = new ArrayList<>();
          for (int i = 0; i < firstCards.size(); i++){
               if (firstCards.get(i) > secondCards.get(i)){
                    winners.add(firstPlayer);
                    break;
               }
               if (firstCards.get(i) < secondCards.get(i)){
                    winners.add(secondPlayer);
                    break;
               }
          }
          if (winners.isEmpty()){
               winners.add(firstPlayer);
               winners.add(secondPlayer);
          }
//          if (firstHighCard >= secondHighCard){
//               winners.add(firstPlayer);
//          }
//          if (secondHighCard >= firstHighCard){
//               winners.add(secondPlayer);
//          }
////          firstHand = getFiveBestCards(firstHand, pokerHand);
////          //          if (pokerHand == TWO_PAIR || pokerHand == FULL_HOUSE)
          return winners;
     }

     private List<Integer> getPrimaryHighCard(List<Card> cards){
          List<Integer> highCards = new ArrayList<>();
          int highCard = 1;
          int secondCard = 0;
          if (pokerHand == PAIR || pokerHand == THREE_KIND || pokerHand == FOUR_KIND || pokerHand == TWO_PAIR || pokerHand == FULL_HOUSE){
               highCard = getPairHighCard(cards, true);
          }
          if (pokerHand == TWO_PAIR || pokerHand == FULL_HOUSE){
               secondCard = getPairHighCard(cards, true);
          }
          if (pokerHand == STRAIGHT || pokerHand == FLUSH){
               //TODO
          }
          highCards.add(highCard);
          if (secondCard > 0){
               highCards.add(secondCard);
          }
          return highCards;
     }

     private int getPairHighCard(List<Card> cards, boolean isHighCard){
          int highCard = 0;

          for (int i = 0; i < cards.size() - 1; i++){
               if (cards.get(i).getValue() == cards.get(i + 1).getValue()){
                    highCard = cards.get(i).getValue();
                    if (isHighCard){
                         break;
                    }
               }
          }
          if (highCard == 0){
               throw new RuntimeException("error calculating pair high card");
          }
          return highCard;
     }

     private List<Card> getFiveBestCards(List<Card> cards, PokerHandEnum pokerHand){
          int highCard;
          if (pokerHand == PAIR){
               for (int i = 0; i < cards.size() - 1; i++){
                    if (cards.get(i).getValue() == cards.get(i + 1).getValue()){
                         highCard = cards.get(i).getValue();
                    }
               }
          }
          return cards;
     }

}
