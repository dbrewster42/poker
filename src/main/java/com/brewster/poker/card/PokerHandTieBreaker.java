package com.brewster.poker.card;

import com.brewster.poker.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     private static final Logger LOGGER = LoggerFactory.getLogger(PokerHandTieBreaker.class);
     private static PokerHandEnum pokerHand;


     public static List<Player> getTieBreaker(Player firstPlayer, Player secondPlayer){
          pokerHand = firstPlayer.getPokerHand();
          //sorted descending
          List<Card> firstHand = firstPlayer.getCards().stream().sorted((a, b) -> b.getValue() - a.getValue()).collect(Collectors.toList());
          List<Card> secondHand = secondPlayer.getCards().stream().sorted((a, b) -> b.getValue() - a.getValue()).collect(Collectors.toList());

          int[] firstCards = getHighCards(firstHand);
          int[] secondCards = getHighCards(secondHand);

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

          return winners;
     }

     private static int[] getHighCards(List<Card> cards){
//          List<Integer> highCards = new ArrayList<>();
          int[] highCards = new int[2];
          int highCard = 0;
          int secondCard = 0;
          if (pokerHand == PAIR || pokerHand == THREE_KIND){
               highCard = getPairHighCard(cards, true);
               secondCard = getHighCard(cards, highCard);
          } else if (pokerHand == TWO_PAIR){
               highCard = getPairHighCard(cards, true);
               secondCard = getPairHighCard(cards, false);
          } else if (pokerHand == FULL_HOUSE || pokerHand == FOUR_KIND){
               highCard = getTripsHighCard(cards);
          } else if (pokerHand == FLUSH){
               cards = getFlushCards(cards);
               highCard = cards.get(0).getValue();
               secondCard = cards.get(1).getValue();
          } else if (pokerHand == STRAIGHT){
               highCard = getStraightHighCard(cards);
          } else if (pokerHand == HIGH_CARD){
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

     private static List<Card> getFlushCards(List<Card> cards){
          for (String suit : DeckBuilder.getSUITS()) {
               int count = 0;
               for (Card card : cards) {
                    if (card.getSuit().equals(suit)) {
                         count++;
                    }
               }
               if (count >= 5) {
                    return cards.stream().filter(v -> v.getSuit().equals(suit)).collect(Collectors.toList());
               }
          }
          throw new RuntimeException("Cards are labeled a flush but couldn't find 5 cards of the same suit");
     }

     private static int getStraightHighCard(List<Card> cards){
          int[] cardValues = cards.stream().mapToInt(Card::getValue).sorted().toArray();
          int start = -1;
          int count = 1;
          int highCard = 0;
          for (int i : cardValues){
               if (start == i){
                    count++;
               } else {
                    if (start - 1 == i){
                         continue;
                    }
                    count = 1;
                    start = i;
               }
               if (count >= 4){
                    highCard = i;
               }
               start++;
          }
          return highCard;
     }
     private static int getTripsHighCard(List<Card> cards){
          int highCard = 0;

          for (int i = 0; i < cards.size() - 1; i++){
               if (cards.get(i).getValue() == cards.get(i + 1).getValue() && cards.get(i).getValue() == cards.get(i + 2).getValue()){
                    highCard = cards.get(i).getValue();
                    break;
               }
          }
          if (highCard == 0){
               throw new RuntimeException("error calculating trips high card");
          }
          return highCard;
     }

     private static void debug(List<Card> cards){
          cards.forEach(v -> LOGGER.info(v.toString()));
     }
}
