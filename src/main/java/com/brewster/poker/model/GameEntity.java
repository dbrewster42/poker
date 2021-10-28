package com.brewster.poker.model;

import com.brewster.poker.card.Card;
import com.brewster.poker.card.DeckBuilder;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.player.Player;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//@Document(collection = "Games")
public class GameEntity {
     private GameType gameType;
     @Id
     @Indexed(unique = true)
     private long id;
     private List<Player> players;
     private List<Player> inactivePlayers;
     private List<Card> cards;
     private List<Card> riverCards;
     private boolean isBet;
     private boolean isDealDone;
     private BetManagerEntity betManagerEntity;
     private boolean useJokers;
     private boolean isActive;

     private int buyIn;
     private int ante;
     private int maxBet;

     private int desiredNumberOfPlayers;
     private int openSlots;
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date createdAt;
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date updatedAt;

     @Transient
     private String tempEmail;


     public GameEntity(){}
     public GameEntity(long id, List<Player> players, GameSettingsRequest settingsRequest){
          this.id = id;
          this.players = players;
          this.desiredNumberOfPlayers = settingsRequest.getNumberOfPlayers();
          openSlots = desiredNumberOfPlayers - 1;
          this.betManagerEntity = new BetManagerEntity(settingsRequest);
          inactivePlayers = new ArrayList<>();
          useJokers = settingsRequest.isHasJokers();
          gameType = GameType.valueOf(settingsRequest.getGameType());
          isActive = true;
          ante = settingsRequest.getAnte();
          buyIn = settingsRequest.getBuyIn();
     }

     private List<Card> setDeck(){
          if (useJokers){
               return DeckBuilder.aDeck().withJokers().build();
          } else {
               return DeckBuilder.aDeck().build();
          }
     }

     public void setGameOver(){
          isDealDone = true;
          isBet = false;
     }

     public boolean applyNewDeal(){
          riverCards = new ArrayList<>();
          this.cards = setDeck();
          isBet = true;
          isDealDone = false;
          players.addAll(inactivePlayers);
          inactivePlayers = new ArrayList<>();
          return ante > 0;
     }

     public void processFold(Player player) {
          players.remove(player);
          inactivePlayers.add(player);
          if (players.size() < 2){
               setGameOver();
          }
          betManagerEntity.setActivePlayersSize(players.size());
     }

     public GameType getGameType() {
          return gameType;
     }

     public void setGameType(GameType gameType) {
          this.gameType = gameType;
     }
     public void setGameType(String gameType){
          this.gameType = GameType.valueOf(gameType);
     }

     public List<String> getBetMessages(){
          return betManagerEntity.getBetMessages();
     }

     public BetManagerEntity getBetManagerEntity() {
          return betManagerEntity;
     }

     public void setBetManagerEntity(BetManagerEntity betManagerEntity) {
          this.betManagerEntity = betManagerEntity;
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

     public void addPlayers(List<Player> players){
          this.players.addAll(players);
     }

     public List<Player> getInactivePlayers() {
          return inactivePlayers;
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

     public void setIsBet(boolean bet) {
          isBet = bet;
     }

     public boolean isDealDone() {
          return isDealDone;
     }

     public void setIsDealDone(boolean dealDone) {
          isDealDone = dealDone;
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

     public String getTempEmail() {
          return tempEmail;
     }

     public void setTempEmail(String tempEmail) {
          this.tempEmail = tempEmail;
     }

     public boolean isActive() {
          return isActive;
     }

     public void setActive(boolean active) {
          isActive = active;
     }

     public int getBuyIn() {
          return buyIn;
     }

     public void setBuyIn(int buyIn) {
          this.buyIn = buyIn;
     }

     public int getAnte() {
          return ante;
     }

     public void setAnte(int ante) {
          this.ante = ante;
     }

     @Override
     public String toString() {
          return "GameEntity{" + "id=" + id + ", players=" + players + ", cards=" + cards + ", riverCards=" + riverCards + ", isBet=" + isBet + ", isDealDone=" + isDealDone + ", betManagerEntity=" + betManagerEntity + ", desiredNumberOfPlayers=" + desiredNumberOfPlayers + '}';
     }
}
