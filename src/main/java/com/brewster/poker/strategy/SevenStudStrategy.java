package com.brewster.poker.strategy;

import com.brewster.poker.card.Card;
import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.response.GameResponse;
import com.brewster.poker.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SevenStudStrategy implements DealStrategy {
     private static final Logger LOGGER = LoggerFactory.getLogger(SevenStudStrategy.class);

     @Override
     public GameResponse dealGameCards(GameEntity gameEntity){
          List<Card> cards = gameEntity.getCards();
          List<Player> players = gameEntity.getPlayers();
          for (Player player : players){
               player.dealCard(cards.get(0));
               cards.remove(0);
          }

          boolean isFull = players.get(0).getCards().size() == 7;
//          List<PlayerDto> playerDtos = players.stream()
//                  .map(v -> new PlayerDto(v, isFull))
//                  .collect(Collectors.toList());
          List<PlayerDto> playerDtos = new ArrayList<>();

          for (Player player : players) {
               PlayerDto playerDto = new PlayerDto(player);
               if (isFull){
                    playerDto.setCards(player.getCards().subList(2, 6));
               } else {
                    playerDto.setCards(player.getCards().subList(2, player.getCards().size()));
               }
               playerDtos.add(playerDto);
          }

          if (isFull){
               gameEntity.setCards(new ArrayList<>());
               gameEntity.setIsDealDone(true);
          }
          gameEntity.setIsBet(true);

          return new GameResponse(playerDtos, isFull);
     }

     @Override
     public void dealPlayerCards(List<Player> players, List<Card> cards) {
          players.forEach(Player::resetCards);
          for (int i = 0; i < 3; i++){
               for (Player player : players){
                    player.dealCard(cards.get(0));
                    cards.remove(0);
               }
          }
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
