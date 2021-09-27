package com.brewster.poker.model;

import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BetManagerEntity {
     private static final Logger LOGGER = LoggerFactory.getLogger(BetManagerEntity.class);
     private int bigBlind;
     private int turnNumber;
     private int turnsLeftInRound;
     private List<Player> activeBetters;
     private int pot = 0;
     private int betAmount;
     private int bigBlindTurn = -1;
     private List<BetEntity> bets;
     private Integer maxBet;

     public BetManagerEntity(GameSettingsRequest settingsRequest){
          this.bigBlind = settingsRequest.getBigBlind();
          this.maxBet = Optional.ofNullable(settingsRequest.getMaxBet()).map(v -> v == 0 ? Integer.MAX_VALUE : v).orElse(bigBlind * 20);
          bets = new ArrayList<>();
     }

     public BetOptions startNewDeal(GameEntity game){
          pot = 0;
          bigBlindTurn++;
          activeBetters = game.getPlayers();
          setAllRoundInformation();
          LOGGER.info("starting new deal with " + turnsLeftInRound + " turns");
          return getBetOptions();
     }

     private void setAllRoundInformation(){
          betAmount = 0;
          turnNumber = bigBlindTurn;
          if (turnNumber == activeBetters.size()){
               LOGGER.info("not enough players left");
               turnNumber = 0;
          }
          currentBetter = activeBetters.get(turnNumber);
          activePlayersSize = activeBetters.size();
          turnsLeftInRound = activePlayersSize;
          activeBetters.forEach(Player::resetCurrentBetAmount);
     }
}
