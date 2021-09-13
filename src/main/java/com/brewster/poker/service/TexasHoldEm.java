package com.brewster.poker.service;

import com.brewster.poker.card.Card;
import com.brewster.poker.card.DeckBuilder;
import com.brewster.poker.game.PokerHand;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.response.EndRoundResponse;
import com.brewster.poker.model.response.NewGameResponse;
import com.brewster.poker.player.ComputerPlayer;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;

import java.util.ArrayList;
import java.util.List;

public class TexasHoldEm implements GameService {
     private int id;
     private List<Player> players;
     private int openSlots;
     private List<Card> cards;
     private List<Card> riverCards = new ArrayList<>();
     private int desiredNumberOfPlayers;
     private BetService betManager;
     private boolean isBet;
     private boolean isLastRound;

     TexasHoldEm(int id, HumanPlayer player, GameSettingsRequest settingsRequest){
          this.id = id;
          this.players = new ArrayList<>();
          players.add(player);
          betManager = new BetService(this, settingsRequest);
          this.desiredNumberOfPlayers = settingsRequest.getNumberOfPlayers();
          openSlots = desiredNumberOfPlayers - 1;
     }
     TexasHoldEm(int id, List<Player> players, GameSettingsRequest settingsRequest){
          this.id = id;
          this.players = players;
          betManager = new BetService(this, settingsRequest);
          this.desiredNumberOfPlayers = settingsRequest.getNumberOfPlayers();
          openSlots = desiredNumberOfPlayers - 1;
          //todo if (request.isCustomRules()){ doSomething() };
     }

     public static GameService createNewGame(int id, HumanPlayer player, GameSettingsRequest settingsRequest){
          return new TexasHoldEm(id, player, settingsRequest);
     }

     public static GameService createNewGame(int id, List<Player> players, GameSettingsRequest settingsRequest){
          return new TexasHoldEm(id, players, settingsRequest);
     }

     public String placeBet(BetRequest betRequest){
          return betManager.placeBet(betRequest);
     }

     public void setGameOver(){
          isLastRound = true;
          isBet = false;
     }

     public EndRoundResponse calculateWinningHand(){
          if (isLastRound && !isBet){
               List<Player> activePlayers = betManager.getActiveBetters();
               List<PlayerDto> playerDtos = new ArrayList<>();
               int winningStrength = 0;
               Player winner = null;
               for (Player player : activePlayers){
                    player.getHand().addAll(riverCards);
//                    PlayerDto playerDto = new PlayerDto(player.getDisplayName(), PokerHand.lookupHand(player.getHand()));
                    PokerHand pokerHand = PokerHand.lookupHand(player.getHand());
                    System.out.println(player.getDisplayName() + " has a " + pokerHand.getHandName());
                    playerDtos.add(new PlayerDto(player.getDisplayName(), pokerHand.getHandName()));
                    player.setPokerHand(pokerHand);
                    int score = pokerHand.getScore();
                    if (winningStrength < score){
                         winningStrength = score;
                         winner = player;
                    } else if (winningStrength == score){
                         //TODO
                         System.out.println("THERE IS A TIE, I WILL ARBITRARILY CHOOSE A WINNER of " + pokerHand.getHandName());
                         PokerHand.getTieBreaker(winner, player);
                    }
               }
               int pot = betManager.getPot();
               winner.collectWinnings(pot);
               PlayerDto playerDto = new PlayerDto(winner.getDisplayName(), winner.getPokerHand().getHandName());
               return new EndRoundResponse(pot, playerDto, playerDtos);
          }
          System.out.println(isLastRound + " - " + isBet);
          throw new IllegalArgumentException("Game is still on-going");
     }

     public BetOptions startNewDeal(){
          cards = getNewStandardDeck();
          dealPlayerCards();
          isBet = true;
          return betManager.startNewDeal();
     }

     public BetOptions getBetOptions(){
          return betManager.getBetOptions();
     }

     public List<Card> startNextRound(){
          if (isBet){
               System.out.println("Cannot deal cards until betting has finished");
               return riverCards;
          }
          if (isLastRound){
               System.out.println("cards have all already been dealt");
               return riverCards;
          }
          betManager.startNextRound();
          isBet = true;
          int count = 1;
          if (riverCards.size() == 0){
               count = 3;
          }
          return dealRiverCardNTimes(count);
     }

     private List<Card> dealRiverCardNTimes(int count){
          System.out.println("dealing " + count + " cards");
          cards.remove(0);
          for (int i = 0; i < count; i++){
               riverCards.add(cards.get(0));
               cards.remove(0);
          }
          if (riverCards.size() == 5){
               isLastRound = true;
          }
          isBet = true;
          return riverCards;
     }
     private List<Card> getNewStandardDeck(){
          return DeckBuilder.aDeck().withStandardDeck().build().getCards();
     }

     private void dealPlayerCards(){
          for (int i = 0; i < 2; i++){
               for (Player player : players){
                    player.dealCard(cards.get(0));
                    cards.remove(0);
               }
          }
     }

     public void addPlayerToGame(HumanPlayer player){
          if (desiredNumberOfPlayers == players.size()){
               for (Player eachPlayer : players){
                    if (eachPlayer.getClass() == ComputerPlayer.class){
                         eachPlayer = player;
                    }
               }
          } else {
               players.add(player);
          }
          openSlots--;
     }
     public void addPlayerToGame(ComputerPlayer player){
          players.add(player);
     }

     public NewGameResponse getNewGameResponse(UserDto userDto){
          List<Card> playerCards = userDto.getPlayer().getHand();
          List<UserDto> users = getUsers();
          users.remove(userDto);
          BetOptions options = betManager.manageComputerBets();

          return new NewGameResponse(id, playerCards, users, options, userDto.getMoney());
     }

     public NewGameResponse getRestartGameResponse(UserDto userDto){
          List<Card> playerCards = userDto.getPlayer().getHand();
          BetOptions options = betManager.manageComputerBets();

          return new NewGameResponse(playerCards, options, userDto.getMoney());
     }

     private List<UserDto> getUsers(){
          List<UserDto> users = new ArrayList<>();
          for (Player player : players){
               player.getUser().setDisplayName(player.getDisplayName());
               users.add(player.getUser());
          }
          return users;
     }


     public int getId() {
          return id;
     }

     public List<Card> getRiverCards() {
          return riverCards;
     }

     public List<Player> getPlayers() {
          return players;
     }

     public void setPlayers(List<Player> players) {
          this.players = players;
     }

     public int getOpenSlots() {
          return openSlots;
     }

     public void setOpenSlots(int openSlots) {
          this.openSlots = openSlots;
     }

     public BetService getBetManager() {
          return betManager;
     }

     public boolean isBet() {
          return isBet;
     }

     public void setIsBet(boolean bet) {
          isBet = bet;
     }

     @Override
     public String toString() {
          return "Game{" +
                  "id=" + id +
                  ", players=" + players +
                  '}';
     }
}
