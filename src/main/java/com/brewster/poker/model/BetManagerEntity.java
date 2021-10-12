package com.brewster.poker.model;

import com.brewster.poker.bet.Bet;
import com.brewster.poker.bet.BlindAction;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BetManagerEntity {
     private int bigBlind;
     private int smallBlind;
     private int turnNumber;
     private int turnsLeftInRound;
     private List<Player> activeBetters;
     private int pot = 0;
     private int betAmount;
     private int bigBlindTurn = -1;
     private List<Bet> bets;
     private List<String> betMessages;
     private Integer maxBet;


     public BetManagerEntity(GameSettingsRequest settingsRequest) {
          this.bigBlind = settingsRequest.getBigBlind();
          this.maxBet = Optional.ofNullable(settingsRequest.getMaxBet()).map(v -> v == 0 ? Integer.MAX_VALUE : v).orElse(bigBlind * 20);
     }

     public void resetBetInfo(List<Player> players){
          pot = bigBlind;
          bigBlindTurn++;
          activeBetters = new ArrayList<>();
          activeBetters.addAll(players);
          betMessages = new ArrayList<>();
          setAllRoundInformation();
//          LOGGER.info("starting new deal with " + turnsLeftInRound + " turns");
          initBigBlind();
//          return getBetOptions();
     }

     public void deal(){
          betMessages.add(" --- *** --- *** --- ");
          setAllRoundInformation();
//          LOGGER.info("starting new round with " + currentBetter.getDisplayName());
     }


     private void initBigBlind(){
//          Player currentBetter = activeBetters.get(turnNumber);
//          currentBetter.betMoney(bigBlind);
//          betAmount = bigBlind;
//        setPot(bigBlind);
//          betMessages.add(currentBetter.getDisplayName() + " posts the $" + bigBlind + "  blind");
          Bet blind = new BlindAction(activeBetters.get(turnNumber), bigBlind, this);
          betMessages.add(blind.process());
          bets.add(blind);
//          adjustTurn();
     }

     public void setAllRoundInformation(){
          betAmount = 0;
          turnNumber = bigBlindTurn;
//          activePlayersSize = activeBetters.size();
          if (turnNumber >= activeBetters.size()){
               turnNumber = 0;
          }
//          currentBetter = activeBetters.get(turnNumber);
          activeBetters.forEach(Player::resetCurrentBetAmount);
          turnsLeftInRound = activeBetters.size();
     }

     public int adjustTurn(){
          turnsLeftInRound--;
          adjustTurnNumber();
          return turnsLeftInRound;
//          currentBetter = activeBetters.get(turnNumber);
//          if (turnsLeftInRound == 0){
//               game.setIsBet(false);
//          }

     }
     private void adjustTurnNumber(){
          turnNumber++;
          if (turnNumber == activeBetters.size()){
               turnNumber = 0;
          }
     }

     public void resetTurnsLeft(){
          turnsLeftInRound = activeBetters.size();
     }

     public void processFold(Player player){
         activeBetters.remove(player);
         turnNumber--;
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

     public int getSmallBlind() {
          return smallBlind;
     }

     public void setSmallBlind(int smallBlind) {
          this.smallBlind = smallBlind;
     }

     public List<String> getBetMessages() {
          return betMessages;
     }

     public void setBetMessages(List<String> betMessages) {
          this.betMessages = betMessages;
     }

     public List<Bet> getBets() {
          return bets;
     }

     public void setBets(List<Bet> bets) {
          this.bets = bets;
     }

     public Integer getMaxBet() {
          return maxBet;
     }

     public void setMaxBet(Integer maxBet) {
          this.maxBet = maxBet;
     }

}
