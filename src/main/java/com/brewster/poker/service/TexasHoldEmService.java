package com.brewster.poker.service;

import com.brewster.poker.card.Card;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.response.EndRoundResponse;
import com.brewster.poker.model.response.GameResponse;
import com.brewster.poker.player.Player;
import com.brewster.poker.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TexasHoldEmService extends GameService {
     private static final Logger LOGGER = LoggerFactory.getLogger(TexasHoldEmService.class);

     public TexasHoldEmService(GameRepository gameRepository, BetService betService, UserService userService){
          super(gameRepository, betService, userService);
     }

     @Override
     public GameResponse deal(GameEntity gameEntity){
          if (gameEntity.isBet()){
               LOGGER.info("Cannot deal cards until betting has finished");
               return new GameResponse(gameEntity.getRiverCards());
          }
          if (gameEntity.isDealDone()){
               LOGGER.info("cards have all already been dealt");
               EndRoundResponse endRoundResponse = calculateWinningHand(gameEntity);
               userService.updateAllPlayersMoney(gameEntity.getPlayers());
               gameEntity.getBetManagerEntity().setPot(0);
               gameRepository.save(gameEntity);
//               gameEntity.getBetManagerEntity().addBetMessage(endRoundResponse.getMessage());
               return new GameResponse(endRoundResponse);
          }
          betService.deal(gameEntity);

          List<Card> riverCards = dealRiverCards(gameEntity);
          gameRepository.save(gameEntity);

          return new GameResponse(riverCards);
     }

     @Override
     public void dealPlayerCards(List<Player> players, List<Card> cards){
          players.forEach(Player::resetCards);
          for (int i = 0; i < 2; i++){
               for (Player player : players){
                    player.dealCard(cards.get(0));
                    cards.remove(0);
               }
          }
     }

     private List<Card> dealRiverCards(GameEntity gameEntity){
          int count = 1;
          List<Card> riverCards = gameEntity.getRiverCards();
          if (riverCards.isEmpty()){
               count = 3;
          }
          LOGGER.info("dealing {} cards", count);
          List<Card> cards = gameEntity.getCards();
          List<Player> players = gameEntity.getPlayers();
          cards.remove(0);
          for (int i = 0; i < count; i++){
               Card nextCard = cards.get(0);
               riverCards.add(nextCard);
               players.forEach(player -> player.dealCard(nextCard));
               cards.remove(0);
          }
          if (riverCards.size() == 5){
               gameEntity.setCards(new ArrayList<>());
               gameEntity.setIsDealDone(true);
          }
          gameEntity.setIsBet(true);
//          gameEntity.setRiverCards(riverCards);
          return riverCards;
     }

}




