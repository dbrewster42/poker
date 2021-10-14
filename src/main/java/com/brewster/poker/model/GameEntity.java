package com.brewster.poker.model;

import com.brewster.poker.card.Card;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.player.Player;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//@Document(collection = "Games")
public class GameEntity {
     @Id
     @Indexed(unique = true)
     private long id;
     private List<Player> players;
     private List<Card> cards;
     private List<Card> riverCards;
     private boolean isBet;
     private boolean isDealDone;
     private BetManagerEntity betManagerEntity;

     private int desiredNumberOfPlayers;
     private int openSlots;
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date createdAt;
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date updatedAt;

//     public GameEntity(Player player, GameSettingsRequest settingsRequest){
//          this.players = new ArrayList<>();
//          players.add(player);
//          this.desiredNumberOfPlayers = settingsRequest.getNumberOfPlayers();
//          openSlots = desiredNumberOfPlayers - 1;
//          this.betManagerEntity = new BetManagerEntity(settingsRequest);
//     }
//     public GameEntity(long id, GameSettingsRequest settingsRequest){
//          this.id = id;
//          this.desiredNumberOfPlayers = settingsRequest.getNumberOfPlayers();
//          openSlots = desiredNumberOfPlayers - 1;
//          this.betManagerEntity = new BetManagerEntity(settingsRequest);
//     }
     public GameEntity(){}
     public GameEntity(long id, List<Player> players, GameSettingsRequest settingsRequest){
          this.id = id;
          this.players = players;
          this.desiredNumberOfPlayers = settingsRequest.getNumberOfPlayers();
          openSlots = desiredNumberOfPlayers - 1;
          this.betManagerEntity = new BetManagerEntity(settingsRequest);
     }

     public void setGameOver(){
          isDealDone = true;
          isBet = false;
     }

     public void applyNewDeal(List<Card> cards){
          riverCards = new ArrayList<>();
          this.cards = cards;
          isBet = true;
          isDealDone = false;
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

     @Override
     public String toString() {
          return "GameEntity{" + "id=" + id + ", players=" + players + ", cards=" + cards + ", riverCards=" + riverCards + ", isBet=" + isBet + ", isDealDone=" + isDealDone + ", betManagerEntity=" + betManagerEntity + ", desiredNumberOfPlayers=" + desiredNumberOfPlayers + '}';
     }
}
