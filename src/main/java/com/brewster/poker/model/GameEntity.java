package com.brewster.poker.model;

import com.brewster.poker.card.Card;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Entity
public class GameEntity {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private long id;

     private List<Player> players;
     private List<Card> cards;
     private List<Card> riverCards;
     private boolean isBet;
     private boolean isDealDone;

     private int bigBlind;
     private int turnNumber;
     private int turnsLeftInRound;
     private List<Player> activeBetters;
     private int pot = 0;
     private int betAmount;
     private int bigBlindTurn = -1;
     @OneToMany
     private List<BetEntity> bets;
     private Integer maxBet;

     private int desiredNumberOfPlayers;
     private int openSlots;
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date createdAt;
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date updatedAt;

     public GameEntity(HumanPlayer player, GameSettingsRequest settingsRequest){
          this.players = new ArrayList<>();
          players.add(player);
          this.desiredNumberOfPlayers = settingsRequest.getNumberOfPlayers();
          openSlots = desiredNumberOfPlayers - 1;
          this.bigBlind = settingsRequest.getBigBlind();
          this.maxBet = Optional.ofNullable(settingsRequest.getMaxBet()).map(v -> v == 0 ? Integer.MAX_VALUE : v).orElse(bigBlind * 20);
          bets = new ArrayList<>();
     }

     public long getId() {
          return id;
     }

     public void setId(long id) {
          this.id = id;
     }

     public List<Player> getPlayers() {
          return players;
     }

     public void setPlayers(List<Player> players) {
          this.players = players;
     }

     public List<Card> getCards() {
          return cards;
     }

     public void setCards(List<Card> cards) {
          this.cards = cards;
     }

     public List<Card> getRiverCards() {
          return riverCards;
     }

     public void setRiverCards(List<Card> riverCards) {
          this.riverCards = riverCards;
     }

     public boolean isBet() {
          return isBet;
     }

     public void setBet(boolean bet) {
          isBet = bet;
     }

     public boolean isDealDone() {
          return isDealDone;
     }

     public void setDealDone(boolean dealDone) {
          isDealDone = dealDone;
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

     public int getDesiredNumberOfPlayers() {
          return desiredNumberOfPlayers;
     }

     public void setDesiredNumberOfPlayers(int desiredNumberOfPlayers) {
          this.desiredNumberOfPlayers = desiredNumberOfPlayers;
     }

     public int getOpenSlots() {
          return openSlots;
     }

     public void setOpenSlots(int openSlots) {
          this.openSlots = openSlots;
     }

     public Integer getMaxBet() {
          return maxBet;
     }

     public void setMaxBet(Integer maxBet) {
          this.maxBet = maxBet;
     }

     public Date getCreatedAt() {
          return createdAt;
     }

     public void setCreatedAt(Date createdAt) {
          this.createdAt = createdAt;
     }

     public Date getUpdatedAt() {
          return updatedAt;
     }

     public void setUpdatedAt(Date updatedAt) {
          this.updatedAt = updatedAt;
     }
}
