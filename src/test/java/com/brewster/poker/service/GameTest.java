package com.brewster.poker.service;

import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.GameType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    GameEntity game = new GameEntity();
//
//    @Test
//    void getNewStandardDeck(){
//        List<Card> standardDeck =
//        assertEquals(52, standardDeck.size());
//    }

     @Test
     void testEnum(){
          game.setGameType("TEXAS_HOLD_EM");
          assertEquals(game.getGameType(), GameType.TEXAS_HOLD_EM);
          game.setGameType("SEVEN_CARD_STUD");
          assertEquals(game.getGameType(), GameType.SEVEN_CARD_STUD);
     }

}
