package com.brewster.poker.service;

import com.brewster.poker.card.Card;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.response.GameResponse;
import com.brewster.poker.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SevenStudService {
     private static final Logger LOGGER = LoggerFactory.getLogger(SevenStudService.class);


     protected GameResponse dealGameCards(GameEntity gameEntity){
          return null;
     }

     protected void dealPlayerCards(List<Player> players, List<Card> cards) {

     }


//     @Override
//     public GameEntity createGame(UserDto userDto, GameSettingsRequest settingsRequest) {
//          gameId = gameRepository.count() + 1;
//          List<Player> players = generatePlayers(userDto, settingsRequest);
//
//          GameEntity game = new GameEntity(gameId++, players, settingsRequest);
//          return gameRepository.save(game);
//     }

}
