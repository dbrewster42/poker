package com.brewster.poker.service;

import com.brewster.poker.card.Card;
import com.brewster.poker.card.DeckBuilder;
import com.brewster.poker.card.PokerHandEnum;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.card.PokerHandTieBreaker;
import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.exception.UserNotFoundException;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.response.EndRoundResponse;
import com.brewster.poker.model.response.GameResponse;
import com.brewster.poker.model.response.NewGameResponse;
import com.brewster.poker.player.ComputerPlayer;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;


public class OldTexasHoldEmService {
     private static final Logger LOGGER = LoggerFactory.getLogger(OldTexasHoldEmService.class);
     private final int id;
     private List<Player> players;
     private int openSlots;
     private List<Card> cards;
     private List<Card> riverCards;
     private int desiredNumberOfPlayers;
     private BetService betManager;
     private boolean isBet;
     private boolean isDealDone;
     private UserService userService;

     OldTexasHoldEmService(int id, HumanPlayer player, GameSettingsRequest settingsRequest, UserService userService){
          this.id = id;
          this.players = new ArrayList<>();
          players.add(player);
//          betManager = new BetService(this, settingsRequest);
          this.desiredNumberOfPlayers = settingsRequest.getNumberOfPlayers();
          openSlots = desiredNumberOfPlayers - 1;
          this.userService = userService;
     }
     OldTexasHoldEmService(int id, List<Player> players, GameSettingsRequest settingsRequest, UserService userService){
          this.id = id;
          this.players = players;
//          betManager = new BetService(this, settingsRequest);
          this.desiredNumberOfPlayers = settingsRequest.getNumberOfPlayers();
          openSlots = desiredNumberOfPlayers - 1;
          //todo if (request.isCustomRules()){ doSomething() };
          this.userService = userService;
     }

     public void setGameOver(){
          isDealDone = true;
          isBet = false;
     }

     private EndRoundResponse calculateWinningHand(){
          if (isDealDone && !isBet){
               List<Player> activePlayers = betManager.getActiveBetters();
               List<Player> winners = new ArrayList<>();
               List<PlayerDto> playerDtos = new ArrayList<>();
               int winningStrength = 0;
               Player winner = null;
               for (Player player : activePlayers){
                    PokerHandEnum pokerHand = PokerHandEnum.lookupHand(player.getCards());
                    LOGGER.info("{} has a {}", player.getDisplayName(), pokerHand.getHandName());
                    player.setPokerHand(pokerHand);
                    playerDtos.add(new PlayerDto(player));
                    int score = pokerHand.getScore();
                    LOGGER.info("previous winning strength = {} and newcomer's score = {}", winningStrength, score);
                    if (score > winningStrength){
                         winningStrength = score;
                         winner = player;
                         winners = List.of(winner);
                    } else if (winningStrength == score){
                         //TODO tiebreaker
                         LOGGER.info("THERE IS A TIE {}", pokerHand.getHandName());
                         LOGGER.info("previous winners - {}", winners.size());
                         if (winners.size() < 2){
                              winners = PokerHandTieBreaker.getTieBreaker(winner, player);
                              if (winners.size() == 1){
                                   winner = winners.get(0);
                              }
                         } else {
                              if (PokerHandTieBreaker.getTieBreaker(winner, player).size() > 1){
                                   winners.add(player);
                              }
                         }
                         winners.forEach(v -> LOGGER.info(v.getDisplayName() + " ***** !"));
                    }
               }
               if (winners.size() > 1){
                    return getTieRoundResponse(winners, playerDtos);
               }
               winner.collectWinnings(betManager.getPot());
               PlayerDto playerDto = new PlayerDto(winner.getDisplayName(), winner.getPokerHand().getHandName());
               return new EndRoundResponse(betManager.getPot(), playerDto, playerDtos);
          }
          LOGGER.info("{} - {}", isDealDone, isBet);
          throw new IllegalArgumentException("Game is still on-going");
     }

     public EndRoundResponse getTieRoundResponse(List<Player> winners, List<PlayerDto> playerDtos){
          winners.forEach(w -> w.collectWinnings(betManager.getPot() / winners.size()));

          StringBuilder stringBuilder = new StringBuilder();
          winners.forEach(v -> stringBuilder.append(v.getDisplayName() + " and "));
          stringBuilder.delete(stringBuilder.length() - 5, stringBuilder.length());
          stringBuilder.append(" have tied and will split the pot of " + betManager.getPot()
                  + "$ with their poker hand of a " + winners.get(0).getPokerHand().getHandName());

          return new EndRoundResponse(stringBuilder.toString(), playerDtos);
     }

     public BetOptions startNewDeal(){
          cards = getNewStandardDeck();
          riverCards = new ArrayList<>();
          dealPlayerCards();
          isBet = true;
          isDealDone = false;
          return betManager.startNewDeal();
     }

     public GameResponse deal(){
          if (isBet){
               LOGGER.info("Cannot deal cards until betting has finished");
               return new GameResponse(riverCards);
          }
          if (isDealDone){
               EndRoundResponse endRoundResponse = calculateWinningHand();
               userService.updateUsersMoney(players);
               betManager.setPot(0);
               betManager.addBetMessage(endRoundResponse.getMessage());
               return new GameResponse(endRoundResponse);
          }
          betManager.deal();
          isBet = true;
          int count = 1;
          if (riverCards.isEmpty()){
               count = 3;
          }
          return new GameResponse(dealRiverCardNTimes(count));
     }

     private void cardsDebug(){
          for (Player each : players){
               LOGGER.info(each.getDisplayName());
               each.getCards().forEach(v -> LOGGER.info(v.toString()));
          }
          LOGGER.info("/n");
     }

     private List<Card> dealRiverCardNTimes(int count){
          LOGGER.info("dealing {} cards", count);
          cards.remove(0);
          for (int i = 0; i < count; i++){
               Card nextCard = cards.get(0);
               riverCards.add(nextCard);
               players.forEach(player -> player.dealCard(nextCard));
               cards.remove(0);
          }
          if (riverCards.size() == 5){
               isDealDone = true;
          }
          isBet = true;
          return riverCards;
     }

     private List<Card> getNewStandardDeck(){
          return DeckBuilder.aDeck().withStandardDeck().build().getCards();
     }

     private void dealPlayerCards(){
          players.forEach(Player::resetCards);
          for (int i = 0; i < 2; i++){
               for (Player player : players){
                    player.dealCard(cards.get(0));
                    cards.remove(0);
               }
          }
     }

     public NewGameResponse getNewGameResponse(UserDto userDto){
          LOGGER.info(userDto.toString());
          List<Card> playerCards = userDto.getPlayer().getCards();
          BetOptions options = betManager.manageComputerBets();
          LOGGER.info(options.toString(), playerCards);
          return new NewGameResponse(id, playerCards, getUsers(userDto), options, userDto.getMoney());
     }

     private List<UserDto> getUsers(UserDto userDto){
          List<UserDto> users = new ArrayList<>();
          for (Player player : players){
               if (!player.getUser().equals(userDto)) {
                    users.add(player.getUser());
                    player.getUser().setDisplayName(player.getDisplayName());
               }
          }
          return users;
     }

     public UserDto getUser(String name){
          Player thisPlayer = players.stream()
                  .filter(v -> v.getDisplayName().equals(name))
                  .findAny()
                  .orElseThrow(()-> new UserNotFoundException());
          return thisPlayer.getUser();
     }

     public void addPlayerToGame(HumanPlayer player){
          if (desiredNumberOfPlayers == players.size()){
               Player playerToRemove = null;
               for (Player eachPlayer : players){
                    if (eachPlayer.getClass() == ComputerPlayer.class){
                         playerToRemove = eachPlayer;
                         break;
                    }
               }
               if (playerToRemove != null){
                    players.remove(playerToRemove);
               }
          }
          players.add(player);
          openSlots--;
     }

     public void addPlayerToGame(ComputerPlayer player){
          players.add(player);
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

     public boolean isDealDone() {
          return isDealDone;
     }

     @Override
     public String toString() {
          return "Game{" +
                  "id=" + id +
                  ", players=" + players +
                  '}';
     }
}
