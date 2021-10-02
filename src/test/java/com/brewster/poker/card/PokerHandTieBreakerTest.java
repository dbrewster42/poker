package com.brewster.poker.card;

import com.brewster.poker.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokerHandTieBreakerTest {
     private PokerHandTieBreaker sut;

//     @BeforeEach
//     void setUp() {
//          sut = new PokerHandTieBreaker(TestDataBuilder.getPlayer(), TestDataBuilder.getPlayer());
//     }

     @Test
     void tieBreaker(){
          sut = new PokerHandTieBreaker(TestDataBuilder.getPlayer(), TestDataBuilder.getPlayer());
          assertEquals(2, sut.getTieBreaker().size());
     }

     @Test
     void getTieBreaker() {
     }
}