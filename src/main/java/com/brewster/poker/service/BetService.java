package com.brewster.poker.service;

import com.brewster.poker.bet.Action;
import com.brewster.poker.bet.Bet;
import com.brewster.poker.bet.BetFactory;
import com.brewster.poker.bet.BetOptions;
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
    //TODO
//    private Player currentBetter;
//    private BetManagerEntity betManager;

    public BetService(BetFactory betFactory){
        this.betFactory = betFactory;
    }

    public int placeBet(GameEntity game, BetRequest betRequest){
        BetManagerEntity betManager = game.getBetManagerEntity();
//        Player player = currentBetter;
//        LOGGER.info(player.getDisplayName() + " is placing bet " + betRequest.toString());
        Player currentBetter = betManager.getActiveBetters().get(betManager.getTurnNumber());
        String validationStatement = validateBet(betRequest, currentBetter);
        if (validationStatement.isEmpty()) {
            Bet bet = betFactory.createBet(currentBetter, betRequest, betManager);
            String message = bet.process();
            LOGGER.info("Bet has been processed - {}", message);

            bet.setMessage(message);
            betManager.getBets().add(bet);

            if (betManager.adjustTurn() == 0){
                game.setIsBet(false);
            }

            return currentBetter.getMoney();
//            int userMoney = currentBetter.getMoney();
////          betsMade.add(new BetDto(bet, returnStatement));
//            adjustTurn();
//            return userMoney;
        } else {
            LOGGER.info("Bet Invalid - {}", validationStatement);
            throw new InvalidBetException(validationStatement);
        }
    }

    private String validateBet(BetRequest betRequest, Player player){
        String validatorError = "";
        if (!betRequest.getUsername().equals(player.getDisplayName())){
            validatorError += "Critical error. The user who placed the bet was not the expected user. ";
        }

        int newBetAmount = betRequest.getBetAmount();
//todo        if (newBetAmount > maxBet){
//            validatorError += "This game has a maximum bet of " + maxBet + ". ";
//        }

        if (player.getClass() == HumanPlayer.class ){
            if (newBetAmount > player.getMoney()){
                validatorError += "You do not have that much money to bet. Until you reload money, your maximum bet is " + player.getMoney() + ". ";
            }
        }
        return validatorError;
    }

//TODO move to BetManagerEntity


    public BetOptions startNewDeal(GameEntity gameEntity){
        gameEntity.getBetManagerEntity().resetBetInfo(gameEntity.getPlayers());
        return getBetOptions(gameEntity);
    }

    public void deal(GameEntity gameEntity){
        gameEntity.getBetManagerEntity().deal();
//        betMessages.add(" --- *** --- *** --- ");

    }


    public BetOptions getBetOptions(GameEntity gameEntity){
//        LOGGER.info("betManager.getBetOptions {}, turnsLeft = {}, turnNumber = {}", currentBetter.getDisplayName(), turnsLeftInRound, turnNumber);
        BetManagerEntity betManager = gameEntity.getBetManagerEntity();
        if (betManager.getTurnsLeftInRound() > 0){
            int betAmount = betManager.getBetAmount();
            Action[] actionOptions = getPossibleBetActions(betAmount);
            return new BetOptions(betManager.getActiveBetters().get(betManager.getTurnNumber()), actionOptions, betAmount, betManager.getPot());
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
        LOGGER.info("betService options = " + options.toString());
        while (options.isBetActive() && options.getPlayer() instanceof ComputerPlayer) {
            ComputerPlayer computerPlayer = (ComputerPlayer) options.getPlayer();
            LOGGER.info("displayName = " + computerPlayer.getDisplayName());
            BetRequest betRequest = computerPlayer.placeBet(options, gameEntity.getBetManagerEntity());
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
