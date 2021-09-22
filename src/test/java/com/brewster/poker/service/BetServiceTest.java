package com.brewster.poker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.brewster.poker.TestDataBuilder.getBetBetRequest;
import static com.brewster.poker.TestDataBuilder.getGameSettingsRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BetServiceTest {
     BetService sut;
     GameService game;

     @BeforeEach
     void setUp() {
          game = mock(GameService.class);
          sut = new BetService(game, getGameSettingsRequest());
     }

//     @Test
//     void placeBet() {
//          game.startNewDeal();
//          sut.placeBet(getBetBetRequest());
//          //TODO
//     }
}