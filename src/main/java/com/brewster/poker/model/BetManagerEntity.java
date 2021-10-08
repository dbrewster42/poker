package com.brewster.poker.model;

import com.brewster.poker.bet.BetFactoryImplementation;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.player.Player;

import java.util.List;
import java.util.Optional;

public class BetManagerEntity {
     private int bigBlind;
     private int turnNumber;
     private int turnsLeftInRound;
     private List<Player> activeBetters;
     private int pot = 0;
     private int betAmount;
     private int bigBlindTurn = -1;
     private List<BetEntity> bets;
     private Integer maxBet;


     public BetManagerEntity(GameSettingsRequest settingsRequest) {
          this.bigBlind = settingsRequest.getBigBlind();
          this.maxBet = Optional.ofNullable(settingsRequest.getMaxBet()).map(v -> v == 0 ? Integer.MAX_VALUE : v).orElse(bigBlind * 20);
     }

     public int getBigBlind() {
          return bigBlind;
     }

     public void setBigBlind(int bigBlind) {
          this.bigBlind = bigBlind;
     }

     public int getTurnNumber() {
          return turnNumber;
     }

     public void setTurnNumber(int turnNumber) {
          this.turnNumber = turnNumber;
     }

     public int getTurnsLeftInRound() {
          return turnsLeftInRound;
     }

     public void setTurnsLeftInRound(int turnsLeftInRound) {
          this.turnsLeftInRound = turnsLeftInRound;
     }

     public List<Player> getActiveBetters() {
          return activeBetters;
     }

     public void setActiveBetters(List<Player> activeBetters) {
          this.activeBetters = activeBetters;
     }

     public int getPot() {
          return pot;
     }

     public void setPot(int pot) {
          this.pot = pot;
     }

     public int getBetAmount() {
          return betAmount;
     }

     public void setBetAmount(int betAmount) {
          this.betAmount = betAmount;
     }

     public int getBigBlindTurn() {
          return bigBlindTurn;
     }

     public void setBigBlindTurn(int bigBlindTurn) {
          this.bigBlindTurn = bigBlindTurn;
     }

     public List<BetEntity> getBets() {
          return bets;
     }

     public void setBets(List<BetEntity> bets) {
          this.bets = bets;
     }

     public Integer getMaxBet() {
          return maxBet;
     }

     public void setMaxBet(Integer maxBet) {
          this.maxBet = maxBet;
     }

}
