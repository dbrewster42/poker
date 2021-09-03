package com.brewster.poker.controller;

import com.brewster.poker.bet.Action;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.exception.InvalidBetException;
import com.brewster.poker.game.Game;
import com.brewster.poker.game.GamesContainer;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.response.BetResponse;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BetControllerTest {
     private BetController betController;
     Game game;
     int id;

     @BeforeEach
     void setUp() {
          betController = new BetController();
          game = GamesContainer.createGame(getUserDto(), getGameSettingsRequest(), getComputerUser());
          id = game.getId();
          game.startNewDeal();
     }

     @Test
     void getBetOptions() {
          BetOptions betOptions = betController.getBetOptions(id);
          assertTrue(betOptions.getPlayer() instanceof HumanPlayer);
//          if (betOptions.isBetActive()){
//               assertEquals(0, game.getBetManager().getTurnsLftInRound());
////          }

     }

     @Test
     void getBetOptions2() {
          List<Player> playerList = game.getPlayers();
          game.startNextRound();
          BetOptions betOptions = betController.getBetOptions(id);
          assertTrue(betOptions.getPlayer() instanceof HumanPlayer);

     }

     @Test
     void bet() {
          BetOptions betOptions = betController.getBetOptions(id);
          BetResponse betResponse;
          if (betOptions.getPot() == 0){
               betResponse = betController.bet(id, getBetBetRequest());
               int size = betResponse.getBets().size();
               assertEquals(20, betResponse.getBets().get(size - 1).getBetAmount());
               assertEquals(betResponse.getMessage(), betResponse.getBets().get(size - 1).getBetMessage());

          } else {
               betResponse = betController.bet(id, getCheckBetRequest(betOptions.getBetAmount()));
               int size = betResponse.getBets().size();
               assertEquals(betOptions.getBetAmount(), betResponse.getBets().get(size - 1).getBetAmount());
               assertEquals(betResponse.getMessage(), betResponse.getBets().get(size - 1).getBetMessage());
          }
          System.out.println(betResponse.getMessage());
     }

     @Test
     void betThrowsException() {
          BetOptions betOptions = betController.getBetOptions(id);
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

     private BetRequest getCheckBetRequest(int amount){
          BetRequest betRequest = new BetRequest();
          betRequest.setBetAmount(amount);
          betRequest.setUsername("BREWSTER");
          betRequest.setAction(Action.CALL.name());
          return betRequest;
     }

     private BetRequest getBetBetRequest(){
          BetRequest betRequest = new BetRequest();
          betRequest.setBetAmount(20);
          betRequest.setUsername("BREWSTER");
          betRequest.setAction(Action.BET.name());
          return betRequest;
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

     private GameSettingsRequest getGameSettingsRequest(){
          GameSettingsRequest gameSettingsRequest = new GameSettingsRequest();
          gameSettingsRequest.setFillWithComputerPlayers(true);
          gameSettingsRequest.setNumberOfPlayers(4);
          gameSettingsRequest.setBigBlind(5);
          gameSettingsRequest.setDisplayName("BREWSTER");
          return gameSettingsRequest;
     }
}