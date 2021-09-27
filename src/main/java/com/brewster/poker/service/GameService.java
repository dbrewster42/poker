package com.brewster.poker.service;

import com.brewster.poker.card.Card;
import com.brewster.poker.controller.GameController;
import com.brewster.poker.exception.GameNotFoundException;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.PlayerEntity;
import com.brewster.poker.model.response.NewGameResponse;
import com.brewster.poker.model.response.PlayerResponse;
import com.brewster.poker.player.ComputerPlayer;
import com.brewster.poker.player.Player;
import com.brewster.poker.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class GameService {
     private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);
     private final GameRepository gameRepository;
     private Player thisPlayer;
     private GameEntity thisGame;
     private final BetService betService;

     public GameService(GameRepository gameRepository, BetService betService){
          this.gameRepository = gameRepository;
          this.betService = betService;
     }


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

     public NewGameResponse getNewGameResponse(GameEntity gameEntity, PlayerEntity playerEntity){
          LOGGER.info(gameEntity.toString());
          List<PlayerResponse> otherPlayers = separateCurrentPlayer(gameEntity, playerEntity);
//          BetOptions options = betManager.manageComputerBets();
          return new NewGameResponse(gameEntity.getId(), thisPlayer.getHand(), otherPlayers, thisPlayer.getMoney());
     }

     public List<PlayerResponse> separateCurrentPlayer(GameEntity gameEntity, PlayerEntity playerEntity){
          List<PlayerResponse> otherPlayers = new ArrayList<>();
          for (Player player : gameEntity.getPlayers()){
               if (player.getDisplayName().equals(playerEntity.getDisplayName())){
                    thisPlayer = player;
               } else {
                    otherPlayers.add(new PlayerResponse(player));
               }
          }
          return otherPlayers;
     }

//     public List<PlayerResponse> getOtherPlayersResponse(PlayerEntity playerDto) {
//          return players.stream()
//                  .filter(v -> !v.getDisplayName().equals(playerDto.getDisplayName()))
//                  .map(v -> new PlayerResponse(v))
//                  .collect(Collectors.toList());
//     }

     public GameEntity findGame(long id){
          Optional<GameEntity> gameEntity = Optional.ofNullable(gameRepository.findById(id))
                  .orElseThrow(() -> new GameNotFoundException("game not found with id " + id)) ;

          return gameEntity.get();
     }

     public List<Card> deal(GameEntity gameEntity){
          if (gameEntity.isBet()){
               LOGGER.info("Cannot deal cards until betting has finished");
               return gameEntity.getRiverCards();
          }
          if (gameEntity.isDealDone()){
               //todo calculateWinner
               LOGGER.info("cards have all already been dealt");
               return gameEntity.getRiverCards();
          }
          betService.deal();
          int count = 1;
          if (gameEntity.getRiverCards().isEmpty()){
               count = 3;
          }
          dealRiverCardNTimes(count, gameEntity);
          gameRepository.save(gameEntity);

          return gameEntity.getRiverCards();
     }

     private List<Card> dealRiverCardNTimes(int count, GameEntity gameEntity){
          LOGGER.info("dealing {} cards", count);
          List<Card> cards = gameEntity.getCards();
          List<Card> riverCards = gameEntity.getRiverCards();
          List<Player> players = gameEntity.getPlayers();
          cards.remove(0);
          for (int i = 0; i < count; i++){
               Card nextCard = cards.get(0);
               riverCards.add(nextCard);
               players.forEach(player -> player.dealCard(nextCard));
               cards.remove(0);
          }
          if (riverCards.size() == 5){
               gameEntity.setDealDone(true);
          }
          gameEntity.setBet(true);
          return riverCards;
     }

     private void debug(){

     }
}
