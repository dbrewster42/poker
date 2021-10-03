package com.brewster.poker.card;

import com.brewster.poker.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.brewster.poker.card.PokerHandEnum.FLUSH;
import static com.brewster.poker.card.PokerHandEnum.FOUR_KIND;
import static com.brewster.poker.card.PokerHandEnum.FULL_HOUSE;
import static com.brewster.poker.card.PokerHandEnum.HIGH_CARD;
import static com.brewster.poker.card.PokerHandEnum.PAIR;
import static com.brewster.poker.card.PokerHandEnum.STRAIGHT;
import static com.brewster.poker.card.PokerHandEnum.THREE_KIND;
import static com.brewster.poker.card.PokerHandEnum.TWO_PAIR;

public class PokerHandTieBreaker {
//     private Player firstPlayer;
//     private Player secondPlayer;
     private static PokerHandEnum pokerHand;

//     public PokerHandTieBreaker(Player first, Player second){
//          this.firstPlayer = first;
//          this.secondPlayer = second;
//          pokerHand = first.getPokerHand();
//
//     }
     public static List<Player> getTieBreaker(Player first, Player second){
          Player firstPlayer = first;
          Player secondPlayer = second;
          pokerHand = first.getPokerHand();
          //sorted descending
          List<Card> firstHand = firstPlayer.getCards().stream().sorted((a, b) -> b.getValue() - a.getValue()).collect(Collectors.toList());
          List<Card> secondHand = secondPlayer.getCards().stream().sorted((a, b) -> b.getValue() - a.getValue()).collect(Collectors.toList());

          int[] firstCards = getPrimaryHighCard(firstHand);
          int[] secondCards = getPrimaryHighCard(secondHand);

          List<Player> winners = new ArrayList<>();
          for (int i = 0; i < firstCards.length; i++){
               if (firstCards[i] > secondCards[i]){
                    winners.add(firstPlayer);
                    break;
               }
               if (firstCards[i] < secondCards[i]){
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
          return winners;
     }

     private static int[] getPrimaryHighCard(List<Card> cards){
//          List<Integer> highCards = new ArrayList<>();
          int[] highCards = new int[2];
          int highCard = 1;
          int secondCard = 1;
          if (pokerHand == PAIR || pokerHand == THREE_KIND || pokerHand == FOUR_KIND){
               highCard = getPairHighCard(cards, true);
               secondCard = getHighCard(cards, highCard);
          } else if (pokerHand == TWO_PAIR || pokerHand == FULL_HOUSE){
               highCard = getPairHighCard(cards, true);
               secondCard = getPairHighCard(cards, false);
          } else if (pokerHand == FLUSH){
               //TODO
          } else if (pokerHand == STRAIGHT){
               //TODO
          }else if (pokerHand == HIGH_CARD){
               highCard = getHighCard(cards, 1);
               secondCard = getHighCard(cards, highCard);
          }

          highCards[0] = highCard;
          highCards[1] = secondCard;

          return highCards;
     }

     private static int getPairHighCard(List<Card> cards, boolean isHighCard){
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
     private static int getHighCard(List<Card> cards, int previousHighCard){
          for (Card card : cards){
               if (card.getValue() != previousHighCard){
                    return card.getValue();
               }
          }
          return 0;
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
