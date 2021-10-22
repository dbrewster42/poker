package com.brewster.poker.utility;

import com.brewster.poker.exception.GameNotFoundException;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.GameType;
import com.brewster.poker.model.response.GameResponse;
import com.brewster.poker.service.SevenStudService;
import com.brewster.poker.service.TexasHoldEmService;
import org.springframework.stereotype.Component;

@Component
public class GameContext {
     private final TexasHoldEmService texasHoldEmService;
     private final SevenStudService sevenStudService;

     public GameContext(TexasHoldEmService texasHoldEmService, SevenStudService sevenStudService){
          this.texasHoldEmService = texasHoldEmService;
          this.sevenStudService = sevenStudService;
     }

     public GameResponse dealGameCards(GameEntity gameEntity){
          if (gameEntity.getGameType().equals(GameType.TEXAS_HOLD_EM)){
               return texasHoldEmService.dealGameCards(gameEntity);
          } else if (gameEntity.getGameType().equals(GameType.SEVEN_CARD_STUD)) {
               return sevenStudService.dealGameCards(gameEntity);
          } else {
               throw new GameNotFoundException("Invalid game type");
          }
     }
     public void dealPlayerCards(GameEntity gameEntity){
          if (gameEntity.getGameType().equals(GameType.TEXAS_HOLD_EM)){
               texasHoldEmService.dealPlayerCards(gameEntity.getPlayers(), gameEntity.getCards());
          } else if (gameEntity.getGameType().equals(GameType.SEVEN_CARD_STUD)) {
               sevenStudService.dealPlayerCards(gameEntity.getPlayers(), gameEntity.getCards());
          } else {
               throw new GameNotFoundException("Invalid game type");
          }
     }

}
