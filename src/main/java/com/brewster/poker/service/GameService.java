package com.brewster.poker.service;

import com.brewster.poker.card.Card;
import com.brewster.poker.controller.GameController;
import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.exception.GameNotFoundException;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.PlayerEntity;
import com.brewster.poker.model.response.NewGameResponse;
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
     private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
     private final GameRepository gameRepository;
//     private final BetService betService;

     public GameService(GameRepository gameRepository){
          this.gameRepository = gameRepository;
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

     public NewGameResponse getNewGameResponse(GameEntity gameEntity, PlayerEntity playerDto){
          LOGGER.info(gameEntity.toString());
          List<Card> playerCards = playerDto.getCards();
//          BetOptions options = betManager.manageComputerBets();
          return new NewGameResponse(gameEntity.getId(), playerCards, gameEntity.getOtherPlayersResponse(playerDto), playerDto.getMoney());
     }

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
          betManager.deal();
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
