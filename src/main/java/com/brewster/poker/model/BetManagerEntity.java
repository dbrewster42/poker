package com.brewster.poker.model;

import com.brewster.poker.bet.Bet;
import com.brewster.poker.bet.BlindAction;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BetManagerEntity {
     private long id;
     private int bigBlind;
     private int smallBlind;
     private int turnNumber;
     private int turnsLeftInRound;
     private int pot = 0;
     private int betAmount;
     private int bigBlindTurn = -1;
     private List<BetEntity> bets;
     private List<String> betMessages;
     private int maxBet;
     private int activePlayersSize;


     public BetManagerEntity(GameSettingsRequest settingsRequest) {
          this.bigBlind = settingsRequest.getBigBlind();
          this.maxBet = Optional.ofNullable(settingsRequest.getMaxBet()).map(v -> v == 0 ? Integer.MAX_VALUE : v).orElse(bigBlind * 20);
          bets = new ArrayList<>();
          betMessages = new ArrayList<>();
     }

     public BetManagerEntity(){}

     public void resetBetInfo(List<Player> players){
          pot = bigBlind;
          bigBlindTurn++;
          activePlayersSize = players.size();
          betMessages = new ArrayList<>();
          setAllRoundInformation();
          initBigBlind(players);
     }

     public void deal(){
          betMessages.add(" --- *** --- *** --- ");
          setAllRoundInformation();
//          LOGGER.info("starting new round with " + currentBetter.getDisplayName());
     }


     private void initBigBlind(List<Player> players){
          Bet blind = new BlindAction(players.get(turnNumber), bigBlind, "BLIND", this);
          betMessages.add(blind.process());
          bets.add(new BetEntity(blind));
          adjustTurn();
     }

     public void setAllRoundInformation(){
          betAmount = 0;
          turnNumber = bigBlindTurn;
          if (turnNumber >= activePlayersSize){
               turnNumber = 0;
          }
          turnsLeftInRound = activePlayersSize;
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
          if (turnNumber == activePlayersSize){
               turnNumber = 0;
          }
     }

     public void resetTurnsLeft(){
          turnsLeftInRound = activePlayersSize;
     }

     public void processFold(Player player){
//         activeBetters.remove(player);
//         if (activePlayersSize < 2){
//
//         }
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

     public List<BetEntity> getBets() {
          return bets;
     }

     public void setBets(List<BetEntity> bets) {
          this.bets = bets;
     }

     public int getMaxBet() {
          return maxBet;
     }

     public void setMaxBet(int maxBet) {
          this.maxBet = maxBet;
     }

     public int getActivePlayersSize() {
          return activePlayersSize;
     }

     public void setActivePlayersSize(int activePlayersSize) {
          this.activePlayersSize = activePlayersSize;
     }

     public long getId() {
          return id;
     }

     public void setId(long id) {
          this.id = id;
     }

     @Override
     public String toString() {
          return "BetManagerEntity{" + "bigBlind=" + bigBlind + ", smallBlind=" + smallBlind + ", turnNumber=" + turnNumber + ", turnsLeftInRound=" + turnsLeftInRound + ", pot=" + pot + ", betAmount=" + betAmount + ", bigBlindTurn=" + bigBlindTurn + ", bets=" + bets + ", betMessages=" + betMessages + ", maxBet=" + maxBet + '}';
     }
}
