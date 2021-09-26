package com.brewster.poker.controller;

import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.response.NewGameResponse;
import com.brewster.poker.service.GameService;
import com.brewster.poker.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
}
