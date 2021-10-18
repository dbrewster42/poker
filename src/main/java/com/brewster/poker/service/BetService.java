package com.brewster.poker.service;

import com.brewster.poker.bet.Action;
import com.brewster.poker.bet.Bet;
import com.brewster.poker.bet.BetFactory;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.bet.FoldAction;
import com.brewster.poker.exception.InvalidBetException;
import com.brewster.poker.model.BetManagerEntity;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.player.ComputerPlayer;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BetService.class);
    private static final Action[] CHECK_ACTIONS = { Action.CHECK, Action.BET, Action.FOLD };
    private static final Action[] CALL_ACTIONS = { Action.CALL, Action.RAISE, Action.FOLD };
    private final BetFactory betFactory;

    public BetService(BetFactory betFactory){
        this.betFactory = betFactory;
    }

    private void playerDebug(GameEntity game){
        game.getPlayers().stream().forEach(v -> LOGGER.info(v.getDisplayName()));
    }

    public int placeBet(GameEntity game, BetRequest betRequest){
        BetManagerEntity betManager = game.getBetManagerEntity();
//        LOGGER.info(player.getDisplayName() + " is placing bet " + betRequest.toString());
        Player currentBetter = game.getPlayers().get(betManager.getTurnNumber());
//        playerDebug(game);
        LOGGER.info("{} is the current better on current turn {}", currentBetter, betManager.getTurnNumber());
        String validationStatement = validateBet(betRequest, currentBetter, betManager.getMaxBet());
        LOGGER.info("placing bet {}", betRequest);
        if (validationStatement.isEmpty()) {
            Bet bet = betFactory.createBet(currentBetter, betRequest, betManager);
            if (bet instanceof FoldAction){ //TODO 1
                game.processFold(currentBetter);
            }
            String message = bet.process();
            LOGGER.info("Bet has been processed - {}", message);

            bet.setMessage(message);
            betManager.getBets().add(bet);//TODO better division between bets and bet messages
            betManager.getBetMessages().add(message);


            if (betManager.adjustTurn() == 0){ //TODO 2
                game.setIsBet(false);
            }

            return currentBetter.getMoney();
        } else {
            LOGGER.info("Bet Invalid - {}", validationStatement);
            throw new InvalidBetException(validationStatement);
        }
    }

    private String validateBet(BetRequest betRequest, Player player, int maxBet){
        String validatorError = "";

        int newBetAmount = betRequest.getBetAmount();
        if (player.getClass() == HumanPlayer.class ){
            if (newBetAmount > player.getMoney()){
                validatorError += "You do not have that much money to bet. Until you reload money, your maximum bet is " + player.getMoney() + ". ";
            }
            if (!betRequest.getUsername().equals(player.getEmail())){
                LOGGER.info("BetRequest user {} does not match expected user {}", betRequest.getUsername(), player.getEmail());
                validatorError += "Critical error. The user who placed the bet was not the expected user. ";
            }
        } else if (!betRequest.getUsername().equals(player.getDisplayName())){
            LOGGER.info("BetRequest user {} does not match expected user {}", betRequest.getUsername(), player.getDisplayName());
            validatorError += "Critical error. The computer user who placed the bet was not the expected user. ";
        }

        if (newBetAmount > maxBet){
            validatorError += "This game has a maximum bet of " + maxBet + ". ";
        }
        return validatorError;
    }

    public void startNewDeal(GameEntity gameEntity){
        gameEntity.getBetManagerEntity().resetBetInfo(gameEntity.getPlayers());
        gameEntity.getPlayers().forEach(Player::resetCurrentBetAmount);
    }

    public void deal(GameEntity gameEntity){
        gameEntity.getBetManagerEntity().deal();
        gameEntity.getPlayers().forEach(Player::resetCurrentBetAmount);
//        betMessages.add(" --- *** --- *** --- ");
    }


    private BetOptions getBetOptions(GameEntity gameEntity){
//        LOGGER.info("betManager.getBetOptions {}, turnsLeft = {}, turnNumber = {}", currentBetter.getDisplayName(), turnsLeftInRound, turnNumber);
        BetManagerEntity betManager = gameEntity.getBetManagerEntity();
        if (betManager.getTurnsLeftInRound() > 0){
            int betAmount = betManager.getBetAmount();
            Action[] actionOptions = getPossibleBetActions(betAmount);
            return new BetOptions(gameEntity.getPlayers().get(betManager.getTurnNumber()), actionOptions, betAmount, betManager.getPot());
        } else {
            LOGGER.info("end of betting round");
            gameEntity.setIsBet(false);
            //FIXME should we auto call or not? if yes, need logic in game controller for Deal
//            game.startNextRound();
            return new BetOptions();
        }
    }

    public BetOptions manageComputerBets(GameEntity gameEntity){
        BetOptions options = getBetOptions(gameEntity);
        while (options.isBetActive() && options.getPlayer() instanceof ComputerPlayer) {
            ComputerPlayer computerPlayer = (ComputerPlayer) options.getPlayer();
            BetRequest betRequest = computerPlayer.placeBet(options, gameEntity.getBetManagerEntity());
            LOGGER.info("computer is making a bet - {}", betRequest.toString());
            placeBet(gameEntity, betRequest);
            options = getBetOptions(gameEntity);
        }
        LOGGER.info("returning betOptions = " + options.toString());

        return options;
    }

    private Action[] getPossibleBetActions(int betAmount){
        if (betAmount > 0){
            return CALL_ACTIONS;
        } else {
            return CHECK_ACTIONS;
        }
    }

//    public void processFold(BetManagerEntity betManager, Player player){
//        betManager.getActiveBetters().remove(player);
//        betManager.setTurnNumber(betManager.getTurnNumber() - 1);
////        updateActivePlayersSize();
//    }



//    public void updateActivePlayersSize(){
//        this.activePlayersSize = activeBetters.size();
//        if (activePlayersSize == 1){
//            game.setGameOver();
//        }
//    }


}
