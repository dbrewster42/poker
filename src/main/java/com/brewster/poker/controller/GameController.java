package com.brewster.poker.controller;

import com.brewster.poker.card.Card;
import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.request.JoinRequest;
import com.brewster.poker.model.response.EndRoundResponse;
import com.brewster.poker.model.response.GameResponse;
import com.brewster.poker.model.response.NewGameResponse;
import com.brewster.poker.player.Player;
import com.brewster.poker.service.GameService;
import com.brewster.poker.service.GamesContainer;
import com.brewster.poker.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("game")
public class GameController {
     private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
     private GameService gameService;
     private PlayerService playerService;

     public GameController(GameService gameService, PlayerService playerService){
          this.gameService = gameService;
          this.playerService = playerService;
     }


     @PostMapping
     public NewGameResponse createGame(@RequestBody GameSettingsRequest request) {
          LOGGER.info(request.toString());
          PlayerDto playerDto = playerService.findPlayer(request.getUsername());

          GameEntity game = new GameEntity(playerDto, request);
          if (request.isFillWithComputerPlayers()){
               gameService.generateNComputerPlayers(game, request.getNumberOfPlayers() - game.getPlayers().size());
          }

          return gameService.getNewGameResponse(game, playerDto);
     }

//     @PostMapping("{id}/restart")
//     public NewGameResponse getNewRound(@PathVariable int id, @RequestBody UserRequest request){
//          LOGGER.info(request.getUsername(), id);
//          game = GamesContainer.findGameById(id);
//          game.startNewDeal();
//          userDto = game.getUser(request.getUsername());
//          LOGGER.info(userDto.toString());
//          return game.getNewGameResponse(userDto);
//     }


     private void debug(GameEntity game){
          for (Player each : game.getPlayers()){
               LOGGER.info(each.getDisplayName());
               each.getHand().forEach(Card::show);
          }
          LOGGER.info("");
     }

     @GetMapping("{id}")
     public GameResponse deal(@PathVariable long id) {
          //TODO change response to GameResponse and then calculate winner automatically after game is over
          GameEntity game = gameService.findGame(id);
          LOGGER.info("dealing card");

          return gameService.deal();
     }

//     @GetMapping("{id}")
//     public List<Card> deal(@PathVariable int id) {
//          //TODO change response to GameResponse and then calculate winner automatically after game is over
//          game = GamesContainer.findGameById(id);
//          LOGGER.info("dealing card");
//
//          return ga.deal();
//     }

     @GetMapping("{id}/winner")
     public EndRoundResponse calculateWinner(@PathVariable int id) {
          game = GamesContainer.findGameById(id);
          return game.calculateWinningHand();
     }

     @GetMapping("join")
     public List<GameService> findGame() {
          return GamesContainer.findAvailableGames();
     }

     @PostMapping("join")
     public void joinGame(@RequestBody JoinRequest request) {
          userDto = userService.findUser(request.getUsername());
          GameService game = GamesContainer.addPlayerToGame(userDto, request);
     }
}
