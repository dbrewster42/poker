package com.brewster.poker.controller;

import com.brewster.poker.TestDataBuilder;
import com.brewster.poker.bet.Action;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.exception.InvalidBetException;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.repository.GameRepository;
import com.brewster.poker.service.BetService;
import com.brewster.poker.service.GameService;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.response.BetResponse;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;
import com.brewster.poker.service.UserService;
import com.brewster.poker.strategy.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.brewster.poker.TestDataBuilder.getBetBetRequest;
import static com.brewster.poker.TestDataBuilder.getCheckBetRequest;
import static com.brewster.poker.TestDataBuilder.getGameSettingsRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BetControllerTest {
     private BetController betController;
     private GameService gameService;
     private BetService betService;
     private GameRepository gameRepository;
     private UserService userService;
     private GameContext gameStrategy;

     GameEntity game;
     long id;

     @BeforeEach
     void setUp() {
          gameRepository = mock(GameRepository.class);
          userService = mock(UserService.class);
          betService = mock(BetService.class);
          gameStrategy = mock(GameContext.class);
          gameService = new GameService(gameRepository, betService, userService, gameStrategy);
          betController = new BetController(betService, gameService);

          UserDto userDto = getUserDto();
          game = gameService.createGame(userDto, getGameSettingsRequest()); //getComputerUser()
          id = game.getId();

          when(betService.manageComputerBets(any())).thenReturn(TestDataBuilder.getBetOptions());
          when(gameRepository.findById(any())).thenReturn(Optional.of(game));
          gameService.startNewDeal(game, userDto); //TODO Get tests working
     }

     @Test
     void getBetOptions() {
          System.out.println("id - " + id);
          BetOptions betOptions = betController.getBetOptions(id).getBetOptions();
          assertTrue(betOptions.getPlayer() instanceof HumanPlayer);
//          if (betOptions.isBetActive()){
//               assertEquals(0, game.getBetManager().getTurnsLftInRound());
////          }

     }

     @Test
     void getBetOptions2() {
          List<Player> playerList = game.getPlayers();
          gameService.deal(game);
          BetOptions betOptions = betController.getBetOptions(id).getBetOptions();
          assertTrue(betOptions.getPlayer() instanceof HumanPlayer);

     }

     @Test
     @Disabled
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
     @Disabled
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
          userDto.setEmail("HUMAN");
          userDto.setMoney(1000);
          userDto.setId("1");
          return userDto;
     }

     private UserDto getComputerUser(){
          UserDto userDto = new UserDto();
          userDto.setMoney(10000);
          userDto.setEmail("HAL");
          return userDto;
     }

}