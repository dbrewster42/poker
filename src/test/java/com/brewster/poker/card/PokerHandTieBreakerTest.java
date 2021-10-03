package com.brewster.poker.card;

import com.brewster.poker.CardHandBuilder;
import com.brewster.poker.TestDataBuilder;
import com.brewster.poker.player.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PokerHandTieBreakerTest {
     private Player player = TestDataBuilder.getPlayer();

     @Test
     void tieBreaker(){
          assertEquals(2, PokerHandTieBreaker.getTieBreaker(player, TestDataBuilder.getPlayer()).size());
     }

     @Test
     void getTieBreaker() {
          Player loser = TestDataBuilder.getPlayer();
          loser.setCards(CardHandBuilder.buildWorsePair());
          List<Player> playerList = PokerHandTieBreaker.getTieBreaker(player, loser);
          assertEquals(player, playerList.get(0));
     }

     @Test
     void getTwoPairTieBreaker() {
          Player player = TestDataBuilder.getTwoPairPlayer();
          Player loser = TestDataBuilder.getTwoPairPlayer();

     }
     @Test
     void getTwoPairTieBreaker2() {

     }
}