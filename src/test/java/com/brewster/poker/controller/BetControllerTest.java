package com.brewster.poker.controller;

import com.brewster.poker.bet.Action;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.exception.InvalidBetException;
import com.brewster.poker.service.GameService;
import com.brewster.poker.service.GamesContainer;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.response.BetResponse;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.brewster.poker.TestDataBuilder.getBetBetRequest;
import static com.brewster.poker.TestDataBuilder.getCheckBetRequest;
import static com.brewster.poker.TestDataBuilder.getGameSettingsRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BetControllerTest {
     private BetController betController;
     private GamesContainer gamesContainer;
     GameService game;
     int id;

     @BeforeEach
     void setUp() {
          gamesContainer = mock(GamesContainer.class);
          betController = new BetController(gamesContainer);
          game = gamesContainer.createGame(getUserDto(), getGameSettingsRequest(), getComputerUser());
          id = game.getId();
          game.startNewDeal();
     }

     @Test
     void getBetOptions() {
          BetOptions betOptions = betController.getBetOptions(id).getBetOptions();
          assertTrue(betOptions.getPlayer() instanceof HumanPlayer);
//          if (betOptions.isBetActive()){
//               assertEquals(0, game.getBetManager().getTurnsLftInRound());
////          }

     }

     @Test
     void getBetOptions2() {
          List<Player> playerList = game.getPlayers();
          game.deal();
          BetOptions betOptions = betController.getBetOptions(id).getBetOptions();
          assertTrue(betOptions.getPlayer() instanceof HumanPlayer);

     }

     @Test
     void bet() {
          BetOptions betOptions = betController.getBetOptions(id).getBetOptions();
          BetResponse betResponse;
          if (betOptions.getPot() == 0){
               betResponse = betController.bet(id, getBetBetRequest());
               int size = betResponse.getMessages().size();
               assertEquals("BREWSTER has made a bet of 20. The total pot is now at 20", betResponse.getMessages().get(size - 1));
               System.out.println(betResponse.getMessages().get(size - 1));
          } else {
               betResponse = betController.bet(id, getCheckBetRequest(betOptions.getBetAmount()));
               int size = betResponse.getMessages().size();
               assertEquals("BREWSTER has called the bet of " + betOptions.getBetAmount() + ". The total pot is now at", betResponse.getMessages().get(size - 1));
               System.out.println(betResponse.getMessages().get(size - 1));
          }
     }

     @Test
     void betThrowsException() {
          BetOptions betOptions = betController.getBetOptions(id).getBetOptions();
          BetRequest betRequest = getBetBetRequest();
          betRequest.setBetAmount(1200);
          if (betOptions.getPot() == 0){

               InvalidBetException exception = assertThrows(
                       InvalidBetException.class, () -> betController.bet(id, betRequest)
               );
               System.out.println(exception.getMessage());
          } else {
               betRequest.setAction(Action.RAISE.name());
               InvalidBetException exception = assertThrows(
                       InvalidBetException.class, () -> betController.bet(id, betRequest)
               );
               System.out.println(exception.getMessage());
          }
     }


     private UserDto getUserDto(){
          UserDto userDto = new UserDto();
          userDto.setUsername("HUMAN");
          userDto.setMoney(1000);
          userDto.setId(1);
          return userDto;
     }

     private UserDto getComputerUser(){
          UserDto userDto = new UserDto();
          userDto.setMoney(10000);
          userDto.setUsername("HAL");
          return userDto;
     }

}