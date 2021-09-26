package com.brewster.poker.service;

import org.junit.jupiter.api.BeforeEach;

import static com.brewster.poker.TestDataBuilder.getGameSettingsRequest;
import static org.mockito.Mockito.mock;

class BetServiceTest {
     BetService sut;
     GameService2 game;

     @BeforeEach
     void setUp() {
          game = mock(GameService2.class);
          sut = new BetService(game, getGameSettingsRequest());
     }

//     @Test
//     void placeBet() {
//          game.startNewDeal();
//          sut.placeBet(getBetBetRequest());
//          //TODO
//     }
}