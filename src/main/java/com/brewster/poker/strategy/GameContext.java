package com.brewster.poker.strategy;

import com.brewster.poker.exception.GameNotFoundException;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.GameType;
import com.brewster.poker.model.response.GameResponse;
import org.springframework.stereotype.Component;

@Component
public class GameContext {
     private final TexasHoldEmStrategy texasHoldEmStrategy;
     private final SevenStudStrategy sevenStudStrategy;
     private DealStrategy dealStrategy;

     public GameContext(TexasHoldEmStrategy texasHoldEmStrategy, SevenStudStrategy sevenStudStrategy){
          this.texasHoldEmStrategy = texasHoldEmStrategy;
          this.sevenStudStrategy = sevenStudStrategy;
     }

     private void chooseDealStrategy(GameType gameType) {
          if (gameType.equals(GameType.TEXAS_HOLD_EM)){
               this.dealStrategy = texasHoldEmStrategy;
          } else if (gameType.equals(GameType.SEVEN_CARD_STUD)) {
               this.dealStrategy = sevenStudStrategy;
          } else {
               throw new GameNotFoundException("Invalid game type");
          }
     }

     public GameResponse dealGameCards(GameEntity gameEntity){
          chooseDealStrategy(gameEntity.getGameType());
          return dealStrategy.dealGameCards(gameEntity);
     }
     public void dealPlayerCards(GameEntity gameEntity){
          chooseDealStrategy(gameEntity.getGameType());
          dealStrategy.dealPlayerCards(gameEntity.getPlayers(), gameEntity.getCards());
     }

}
