package com.brewster.poker.controller;

import com.brewster.poker.bets.BetOptions;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.game.Game;
import com.brewster.poker.game.GamesContainer;
import com.brewster.poker.model.request.GameSettingsRequest;
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
//
//          } else {
//               assertEquals(0, game.getBetManager().getTurnsLeftInRound());
//          }

     }

     @Test
     void getBetOptions2() {
          List<Player> playerList = game.getPlayers();
          game.startNextRound();
          BetOptions betOptions = betController.getBetOptions(id);
          assertTrue(betOptions.getPlayer() instanceof HumanPlayer);

          if (betOptions.isBetActive()){

          } else {
               assertEquals(0, game.getBetManager().getTurnsLeftInRound());
          }

     }

     @Test
     void bet() {
     }

     private UserDto getUserDto(){
          UserDto userDto = new UserDto();
          userDto.setUsername("HUMAN");
          userDto.setDisplayName("HUMAN");
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