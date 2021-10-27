package com.brewster.poker.service;

import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.card.Card;
import com.brewster.poker.card.PokerHandEnum;
import com.brewster.poker.card.PokerHandTieBreaker;
import com.brewster.poker.dto.Dto;
import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.exception.GameNotFoundException;
import com.brewster.poker.exception.UserNotFoundException;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.response.EndRoundResponse;
import com.brewster.poker.model.response.GameResponse;
import com.brewster.poker.model.response.NewGameResponse;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;
import com.brewster.poker.repository.GameRepository;
import com.brewster.poker.strategy.GameContext;
import com.brewster.poker.utility.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);
    private final GameRepository gameRepository;
    private final BetService betService;
    private final UserService userService;
    private long gameId;
    private final GameContext gameContext;


    public GameService(GameRepository gameRepository, BetService betService, UserService userService, GameContext gameContext){
        this.gameRepository = gameRepository;
        this.betService = betService;
        this.userService = userService;
        this.gameContext = gameContext;
    }

    public GameResponse deal(GameEntity gameEntity){
        if (gameEntity.isBet()){
            LOGGER.info("Cannot deal cards until betting has finished");
            throw new IllegalArgumentException("Cannot deal cards until betting has finished");
//            return new GameResponse(gameEntity.getRiverCards());
        }
        if (gameEntity.isDealDone()){
            LOGGER.info("cards have all already been dealt");
            EndRoundResponse endRoundResponse = calculateWinningHand(gameEntity);
            userService.updateAllPlayersMoney(gameEntity.getPlayers());
            gameEntity.getBetManagerEntity().setPot(0);
            gameRepository.save(gameEntity);
            return new GameResponse(endRoundResponse);
        }
        betService.deal(gameEntity);

        GameResponse gameResponse = gameContext.dealGameCards(gameEntity);
        gameRepository.save(gameEntity);

        return gameResponse;
    }

    public GameEntity createGame(UserDto userDto, GameSettingsRequest settingsRequest){
        gameId = gameRepository.count() + 1;
        List<Player> players = generatePlayers(userDto, settingsRequest);

        GameEntity game = new GameEntity(gameId++, players, settingsRequest);
        return gameRepository.save(game);
    }

    private List<Player> generatePlayers(UserDto userDto, GameSettingsRequest settingsRequest){
        List<Player> players;
        if (settingsRequest.isFillWithComputerPlayers()){
            UserDto computer = userService.retrieveComputerUser();
            players = Utils.generateNComputerPlayers(settingsRequest.getNumberOfPlayers() - 1, computer);
        } else {
            players = new ArrayList<>();
        }
        players.add(new HumanPlayer(settingsRequest.getDisplayName(), userDto));
        return players;
    }

    public GameEntity findGame(long id){
        Optional<GameEntity> gameEntity = gameRepository.findById(id);
        if (!gameEntity.isPresent()){
            throw new GameNotFoundException();
        }
        return gameEntity.get();
    }


    public void saveGame(GameEntity gameEntity){
        gameRepository.save(gameEntity);
    }

    public NewGameResponse startNewDeal(GameEntity gameEntity, UserDto userDto){
        LOGGER.info("starting new deal with {}", userDto);

        gameEntity.applyNewDeal();
        gameContext.dealPlayerCards(gameEntity);
        betService.startNewDeal(gameEntity);

        return getNewGameResponse(gameEntity, userDto);
    }
    public NewGameResponse startNewDeal(GameEntity gameEntity, String email){
        gameEntity.applyNewDeal();
        gameContext.dealPlayerCards(gameEntity);
        betService.startNewDeal(gameEntity);
        UserDto userDto = getThisUser(gameEntity, email);

        return getNewGameResponse(gameEntity, userDto);
    }


    EndRoundResponse calculateWinningHand(GameEntity gameEntity){
        if (gameEntity.isDealDone() && !gameEntity.isBet()){
            List<Player> activePlayers = gameEntity.getPlayers();
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
                        } else {
                            winner = player;
                            winners = List.of(winner);
                        }
                    }
                    winners.forEach(v -> LOGGER.info("winner {} ***** !", v.getDisplayName()));
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

    private EndRoundResponse getTieRoundResponse(List<Player> winners, List<PlayerDto> playerDtos, int pot){
        winners.forEach(w -> w.collectWinnings(pot / winners.size()));

        StringBuilder stringBuilder = new StringBuilder();
        winners.forEach(v -> stringBuilder.append(v.getDisplayName() + " and "));
        stringBuilder.delete(stringBuilder.length() - 5, stringBuilder.length());
        stringBuilder.append(" have tied and will split the pot of " + pot
                + "$ with their poker hand of a " + winners.get(0).getPokerHand().getHandName());

        return new EndRoundResponse(stringBuilder.toString(), playerDtos);
    }

    private NewGameResponse getNewGameResponse(GameEntity gameEntity, UserDto userDto){
        Player thisPlayer = getThisPlayer(gameEntity, userDto.getEmail());
        List<Card> playerCards = thisPlayer.getCards();
        BetOptions options = betService.manageComputerBets(gameEntity);
        LOGGER.info(options.toString(), playerCards);
        gameRepository.save(gameEntity);

        List<Dto> players = gameContext.getPlayers(gameEntity, userDto);
        return new NewGameResponse(gameEntity.getId(), playerCards, players, options, userDto.getMoney());

//        if (gameEntity.getGameType().equals(GameType.TEXAS_HOLD_EM)){
//            return new NewGameResponse(gameEntity.getId(), playerCards, getUsers(gameEntity, userDto), options, userDto.getMoney());
//        } else { //TODO
//            return new NewGameResponse(gameEntity.getId(), playerCards, options, userDto.getMoney(), getPlayers(gameEntity, userDto));
//        }
    }

    private UserDto getThisUser(GameEntity gameEntity, String email){
        Player thisPlayer = gameEntity.getPlayers().stream()
                .filter(v -> v.getEmail().equals(email))
                .findAny()
                .orElseThrow(UserNotFoundException::new);

        return new UserDto(thisPlayer);
    }

    private Player getThisPlayer(GameEntity gameEntity, String email){
        return gameEntity.getPlayers().stream()
                .filter(v -> v.getEmail().equals(email))
                .findAny()
                .orElseThrow(UserNotFoundException::new);
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

}