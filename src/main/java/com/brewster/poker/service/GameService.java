package com.brewster.poker.service;

import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.card.Card;
import com.brewster.poker.controller.GameController;
import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.response.NewGameResponse;
import com.brewster.poker.player.ComputerPlayer;
import com.brewster.poker.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameService {
     private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

     public void generateNComputerPlayers(GameEntity gameEntity, int n){
          List<Player> players = new ArrayList<>();
          Random random = new Random();
          for (int i = 0; i < n; i++) {
               String displayName = "HAL" + random.nextInt(500);
               Player player = new ComputerPlayer(displayName);
               players.add(player);
          }
          gameEntity.addPlayers(players);
     }

     public NewGameResponse getNewGameResponse(GameEntity gameEntity, PlayerDto playerDto){
          LOGGER.info(gameEntity.toString());
          List<Card> playerCards = playerDto.getCards();
//          BetOptions options = betManager.manageComputerBets();
          return new NewGameResponse(gameEntity.getId(), playerCards, gameEntity.getPlayers(), playerDto.getMoney());
     }

     private void debug(){

     }
}
