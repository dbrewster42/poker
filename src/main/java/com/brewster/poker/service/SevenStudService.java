package com.brewster.poker.service;

import com.brewster.poker.dto.UserDto;
import com.brewster.poker.exception.GameNotFoundException;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.response.GameResponse;
import com.brewster.poker.model.response.NewGameResponse;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SevenStudService implements GameService {
     private static final Logger LOGGER = LoggerFactory.getLogger(SevenStudService.class);
     private final GameRepository gameRepository;
     private final BetService betService;
     private final UserService userService;
     private long gameId;

     public SevenStudService(GameRepository gameRepository, BetService betService, UserService userService){
          this.gameRepository = gameRepository;
          this.betService = betService;
          this.userService = userService;
     }

     @Override
     public GameEntity createGame(UserDto userDto, GameSettingsRequest settingsRequest) {
          return null;
     }

     @Override
     public GameEntity findGame(long id) {
          Optional<GameEntity> gameEntity = gameRepository.findById(id);
          if (!gameEntity.isPresent()){
               throw new GameNotFoundException();
          }
          return gameEntity.get();
     }

     @Override
     public NewGameResponse startNewDeal(GameEntity gameEntity, UserDto userDto) {
          return null;
     }

     @Override
     public NewGameResponse startNewDeal(GameEntity gameEntity, String email) {
          return null;
     }

     @Override
     public GameResponse deal(GameEntity gameEntity) {
          return null;
     }

     @Override
     public void addPlayerToGame(GameEntity gameEntity, HumanPlayer player) {

     }

     @Override
     public void saveGame(GameEntity gameEntity) {

     }
}
