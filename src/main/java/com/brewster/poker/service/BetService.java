package com.brewster.poker.service;

import com.brewster.poker.bet.Action;
import com.brewster.poker.bet.Bet;
import com.brewster.poker.bet.BetFactory;
import com.brewster.poker.bet.BetFactoryImplementation;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.exception.InvalidBetException;
import com.brewster.poker.model.BetManagerEntity;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.player.ComputerPlayer;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BetService.class);
    private static final Action[] CHECK_ACTIONS = { Action.CHECK, Action.BET, Action.FOLD };
    private static final Action[] CALL_ACTIONS = { Action.CALL, Action.RAISE, Action.FOLD };
    private final GameService gameService;
    private final BetFactory betFactory;

    public BetService(GameService gameService, BetFactory betFactory){
        this.gameService = gameService;
        this.betFactory = betFactory;
    }

    public int placeBet(BetRequest betRequest){
//        Player player = currentBetter;
//        LOGGER.info(player.getDisplayName() + " is placing bet " + betRequest.toString());
        String validationStatement = validateBet(betRequest, currentBetter);
        if (validationStatement.isEmpty()) {
            Bet bet = betFactory.createBet(currentBetter, betRequest, this);
            String message = bet.process();
            LOGGER.info("Bet has been processed - {}", message);

            betMessages.add(message);
            int userMoney = currentBetter.getMoney();
//          betsMade.add(new BetDto(bet, returnStatement));
            adjustTurn();
            return userMoney;
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
//        if (newBetAmount > maxBet){
//            validatorError += "This game has a maximum bet of " + maxBet + ". ";
//        }

        if (player.getClass() == HumanPlayer.class ){
            if (newBetAmount > player.getMoney()){
                validatorError += "You do not have that much money to bet. Until you reload money, your maximum bet is " + player.getMoney() + ". ";
            }
        }
        return validatorError;
    }


    private void adjustTurn(){
        LOGGER.info("Adjusting turn - " + turnsLeftInRound);
        turnsLeftInRound--;
        adjustTurnNumber();
        currentBetter = activeBetters.get(turnNumber);
        if (turnsLeftInRound == 0){
            game.setIsBet(false);
            LOGGER.info("Round over");
        }
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

    protected void deal(){
        betMessages.add(" --- *** --- *** --- ");
        setAllRoundInformation();
        LOGGER.info("starting new round with " + currentBetter.getDisplayName());
    }

    private void initBigBlind(){
        currentBetter.betMoney(bigBlind);
        betAmount = bigBlind;
//        setPot(bigBlind);
        betMessages.add(currentBetter.getDisplayName() + " posts the $" + bigBlind + "  blind");
        adjustTurn();
    }

    protected BetOptions startNewDeal(){
        pot = bigBlind;
        bigBlindTurn++;
        activeBetters = new ArrayList<>();
        activeBetters.addAll(game.getPlayers());
        betMessages = new ArrayList<>();
        setAllRoundInformation();
        LOGGER.info("starting new deal with " + turnsLeftInRound + " turns");
        initBigBlind();
        return getBetOptions();
    }

    private void setAllRoundInformation(){
        betAmount = 0;
        turnNumber = bigBlindTurn;
        activePlayersSize = activeBetters.size();
        if (turnNumber >= activePlayersSize){
            LOGGER.info("not enough players left");
            turnNumber = 0;
        }
        currentBetter = activeBetters.get(turnNumber);
        activeBetters.forEach(Player::resetCurrentBetAmount);
        turnsLeftInRound = activePlayersSize;
    }

    public BetOptions getBetOptions(){
        LOGGER.info("betManager.getBetOptions {}, turnsLeft = {}, turnNumber = {}", currentBetter.getDisplayName(), turnsLeftInRound, turnNumber);
        if (turnsLeftInRound > 0){
            Action[] actionOptions = getPossibleBetActions(betAmount);
            return new BetOptions(currentBetter, actionOptions, betAmount, pot);
        } else {
            LOGGER.info("end of betting round");
            game.setIsBet(false);
            //FIXME should we auto call or not? if yes, need logic in game controller for Deal
//            game.startNextRound();
            return new BetOptions();
        }
    }

    public BetOptions manageComputerBets(){
        BetOptions options = getBetOptions();
        LOGGER.info("betManager options = " + options.toString());
        while (options.isBetActive() && options.getPlayer() instanceof ComputerPlayer) {
            ComputerPlayer computerPlayer = (ComputerPlayer) options.getPlayer();
            LOGGER.info("displayName = " + computerPlayer.getDisplayName());
            computerPlayer.placeBet(options, this);
            options = getBetOptions();
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

    public void processFold(BetManagerEntity betManager, Player player){
        betManager.getActiveBetters().remove(player);
        betManager.setTurnNumber(betManager.getTurnNumber() - 1);
//        updateActivePlayersSize();
    }



//    public void updateActivePlayersSize(){
//        this.activePlayersSize = activeBetters.size();
//        if (activePlayersSize == 1){
//            game.setGameOver();
//        }
//    }


}
