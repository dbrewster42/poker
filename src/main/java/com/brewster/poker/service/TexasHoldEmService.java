package com.brewster.poker.service;

import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.card.Card;
import com.brewster.poker.card.DeckBuilder;
import com.brewster.poker.card.PokerHandEnum;
import com.brewster.poker.card.PokerHandTieBreaker;
import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.exception.GameNotFoundException;
import com.brewster.poker.exception.UserNotFoundException;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.response.EndRoundResponse;
import com.brewster.poker.model.response.GameResponse;
import com.brewster.poker.model.response.NewGameResponse;
import com.brewster.poker.player.ComputerPlayer;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;
import com.brewster.poker.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class TexasHoldEmService implements GameService {
     private static final Logger LOGGER = LoggerFactory.getLogger(TexasHoldEmService.class);
     private final GameRepository gameRepository;
     private Player thisPlayer;
     private GameEntity thisGame;
     private final BetService betService;
     private final UserService userService;
     private Random random = new Random();

     public TexasHoldEmService(GameRepository gameRepository, BetService betService, UserService userService){
          this.gameRepository = gameRepository;
          this.betService = betService;
          this.userService = userService;
     }


     public GameEntity createGame(UserDto userDto, GameSettingsRequest settingsRequest, UserDto computerUser){
          List<Player> players = generateNComputerPlayers(settingsRequest.getNumberOfPlayers() - 1, computerUser);
          HumanPlayer player = convertUserToPlayer(userDto, settingsRequest.getDisplayName());
          GameEntity game = new GameEntity(players, settingsRequest);
          players.add(player);
          return game;
     }

     public List<Player> generateNComputerPlayers(int n, UserDto computer){
          List<Player> players = new ArrayList<>();
          for (int i = 0; i < n; i++) {
               String displayName = "HAL" + random.nextInt(500);
               Player player = new ComputerPlayer(displayName, computer);
               players.add(player);
          }
          return players;
     }

     private HumanPlayer convertUserToPlayer(UserDto userDto, String displayName){
          HumanPlayer player = new HumanPlayer(displayName, userDto);
          userDto.setPlayer(player);
          return player;
     }
     public GameEntity findGame(long id){
          Optional<GameEntity> gameEntity = Optional.ofNullable(gameRepository.findById(id))
                  .orElseThrow(() -> new GameNotFoundException()) ;
//                  .orElseThrow(() -> new GameNotFoundException("game not found with id " + id)) ;

          return gameEntity.get();
     }
     private List<Card> getNewStandardDeck(){
          return DeckBuilder.aDeck().withStandardDeck().build().getCards();
     }
     private void dealPlayerCards(List<Player> players, List<Card> cards){
          players.forEach(Player::resetCards);
          for (int i = 0; i < 2; i++){
               for (Player player : players){
                    player.dealCard(cards.get(0));
                    cards.remove(0);
               }
          }
     }

     public NewGameResponse startNewDeal(GameEntity gameEntity, UserDto userDto){
//          GameEntity gameEntity = findGame(id);
          List<Card> cards = getNewStandardDeck();
          gameEntity.setRiverCards(new ArrayList<>());
          dealPlayerCards(gameEntity.getPlayers(), cards);
          gameEntity.setCards(cards);
          gameEntity.setIsBet(true);
          gameEntity.setIsDealDone(false);

          betService.startNewDeal(gameEntity);
          return getNewGameResponse(gameEntity, userDto);
     }

     public GameResponse deal(GameEntity gameEntity){
          if (gameEntity.isBet()){
               LOGGER.info("Cannot deal cards until betting has finished");
               return new GameResponse(gameEntity.getRiverCards());
          }
          if (gameEntity.isDealDone()){
               //todo calculateWinner
               LOGGER.info("cards have all already been dealt");
               EndRoundResponse endRoundResponse = calculateWinningHand(gameEntity);
               userService.updateUsersMoney(gameEntity.getPlayers());
               gameEntity.getBetManagerEntity().setPot(0);
//               gameEntity.getBetManagerEntity().addBetMessage(endRoundResponse.getMessage());
               return new GameResponse(endRoundResponse);
          }
          betService.deal(gameEntity);
          int count = 1;
          if (gameEntity.getRiverCards().isEmpty()){
               count = 3;
          }
          dealRiverCardNTimes(count, gameEntity);
          gameRepository.save(gameEntity);

          return new GameResponse(gameEntity.getRiverCards());
     }

     private List<Card> dealRiverCardNTimes(int count, GameEntity gameEntity){
          LOGGER.info("dealing {} cards", count);
          List<Card> cards = gameEntity.getCards();
          List<Card> riverCards = gameEntity.getRiverCards();
          List<Player> players = gameEntity.getPlayers();
          cards.remove(0);
          for (int i = 0; i < count; i++){
               Card nextCard = cards.get(0);
               riverCards.add(nextCard);
               players.forEach(player -> player.dealCard(nextCard));
               cards.remove(0);
          }
          if (riverCards.size() == 5){
               gameEntity.setIsDealDone(true);
          }
          gameEntity.setIsBet(true);
          return riverCards;
     }

     private EndRoundResponse calculateWinningHand(GameEntity gameEntity){
          if (gameEntity.isDealDone() && !gameEntity.isBet()){
               List<Player> activePlayers = gameEntity.getBetManagerEntity().getActiveBetters();
               List<Player> winners = new ArrayList<>();
               List<PlayerDto> playerDtos = new ArrayList<>();
               int winningStrength = 0;
               int pot = gameEntity.getBetManagerEntity().getPot();
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
                    return getTieRoundResponse(winners, playerDtos, pot);
               }
               winner.collectWinnings(pot);
               PlayerDto playerDto = new PlayerDto(winner.getDisplayName(), winner.getPokerHand().getHandName());
               return new EndRoundResponse(pot, playerDto, playerDtos);
          }
          LOGGER.info("{} - {}", gameEntity.isDealDone(), gameEntity.isBet());
          throw new IllegalArgumentException("Game is still on-going");
     }

     public EndRoundResponse getTieRoundResponse(List<Player> winners, List<PlayerDto> playerDtos, int pot){
          winners.forEach(w -> w.collectWinnings(pot / winners.size()));

          StringBuilder stringBuilder = new StringBuilder();
          winners.forEach(v -> stringBuilder.append(v.getDisplayName() + " and "));
          stringBuilder.delete(stringBuilder.length() - 5, stringBuilder.length());
          stringBuilder.append(" have tied and will split the pot of " + pot
                  + "$ with their poker hand of a " + winners.get(0).getPokerHand().getHandName());

          return new EndRoundResponse(stringBuilder.toString(), playerDtos);
     }

     public void setGameOver(GameEntity gameEntity){
          gameEntity.setIsDealDone(true);
          gameEntity.setIsBet(false);
     }

     public NewGameResponse getNewGameResponse(GameEntity gameEntity, UserDto userDto){
          LOGGER.info(userDto.toString());
          List<Card> playerCards = userDto.getPlayer().getCards();
          BetOptions options = betService.manageComputerBets(gameEntity);
          LOGGER.info(options.toString(), playerCards);
          return new NewGameResponse(gameEntity.getId(), playerCards, getUsers(gameEntity, userDto), options, userDto.getMoney());
     }

     private List<UserDto> getUsers(GameEntity gameEntity, UserDto userDto){
          List<UserDto> users = new ArrayList<>();
          for (Player player : gameEntity.getPlayers()){
               if (!player.getUser().equals(userDto)) {
                    users.add(player.getUser());
                    player.getUser().setDisplayName(player.getDisplayName());
               }
          }
          return users;
     }

     public UserDto getThisUser(GameEntity gameEntity, String name){
          Player thisPlayer = gameEntity.getPlayers().stream()
                  .filter(v -> v.getDisplayName().equals(name))
                  .findAny()
                  .orElseThrow(()-> new UserNotFoundException());
          return thisPlayer.getUser();
     }

     public void addPlayerToGame(GameEntity gameEntity, HumanPlayer player){
//          if (desiredNumberOfPlayers == players.size()){
//               Player playerToRemove = null;
//               for (Player eachPlayer : players){
//                    if (eachPlayer.getClass() == ComputerPlayer.class){
//                         playerToRemove = eachPlayer;
//                         break;
//                    }
//               }
//               if (playerToRemove != null){
//                    players.remove(playerToRemove);
//               }
//          }
//          players.add(player);
//          openSlots--;
     }

/* I DONT THINK SO
     public NewGameResponse getNewGameResponse(GameEntity gameEntity, PlayerEntity playerEntity){
          LOGGER.info(gameEntity.toString());
          List<PlayerResponse> otherPlayers = separateCurrentPlayer(gameEntity, playerEntity);
          return new NewGameResponse(gameEntity.getId(), thisPlayer.getHand(), otherPlayers, thisPlayer.getMoney());
     }

     public List<PlayerResponse> separateCurrentPlayer(GameEntity gameEntity, PlayerEntity playerEntity){
          List<PlayerResponse> otherPlayers = new ArrayList<>();
          for (Player player : gameEntity.getPlayers()){
               if (player.getDisplayName().equals(playerEntity.getDisplayName())){
                    thisPlayer = player;
               } else {
                    otherPlayers.add(new PlayerResponse(player));
               }
          }
          return otherPlayers;
     }
*/
//     public List<PlayerResponse> getOtherPlayersResponse(PlayerEntity playerDto) {
//          return players.stream()
//                  .filter(v -> !v.getDisplayName().equals(playerDto.getDisplayName()))
//                  .map(v -> new PlayerResponse(v))
//                  .collect(Collectors.toList());
//     }



}