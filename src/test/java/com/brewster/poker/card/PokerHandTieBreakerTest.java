package com.brewster.poker.card;

import com.brewster.poker.CardHandBuilder;
import com.brewster.poker.TestDataBuilder;
import com.brewster.poker.player.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.brewster.poker.CardHandBuilder.buildHigherLowTwoPairWithSevenCards;
import static com.brewster.poker.CardHandBuilder.buildLowerHighTwoPairWithSevenCards;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PokerHandTieBreakerTest {
     private Player player = TestDataBuilder.getPlayer();

     @Test
     void getTieBreaker(){
          assertEquals(2, PokerHandTieBreaker.getTieBreaker(player, TestDataBuilder.getPlayer()).size());
     }

     @Test
     void getTieBreakerPair() {
          Player loser = TestDataBuilder.getPlayer();
          loser.setCards(CardHandBuilder.buildWorsePair());
          List<Player> playerList = PokerHandTieBreaker.getTieBreaker(player, loser);
          assertEquals(player, playerList.get(0));
     }

     @Test
     void getTieBreakerPairHighCard() {
          Player winner = TestDataBuilder.getPlayer();
          winner.setCards(CardHandBuilder.buildPairWithBetterHighCardWithSeven());
          List<Player> playerList = PokerHandTieBreaker.getTieBreaker(player, winner);
          assertEquals(winner, playerList.get(0));
     }

     @Test
     void getTwoPairTieBreaker() {
          Player player = TestDataBuilder.getTwoPairPlayer();
          Player loser = TestDataBuilder.getTwoPairPlayer();
          loser.setCards(buildLowerHighTwoPairWithSevenCards());

          List<Player> playerList = PokerHandTieBreaker.getTieBreaker(player, loser);

          assertEquals(player, playerList.get(0));
          assertEquals(1, playerList.size());
     }
     @Test
     void getTwoPairTieBreakerLowPair() {
          Player player = TestDataBuilder.getTwoPairPlayer();
          Player winner = TestDataBuilder.getTwoPairPlayer();
          winner.setCards(buildHigherLowTwoPairWithSevenCards());

          List<Player> playerList = PokerHandTieBreaker.getTieBreaker(player, winner);

          assertEquals(winner, playerList.get(0));
          assertEquals(1, playerList.size());
     }
}