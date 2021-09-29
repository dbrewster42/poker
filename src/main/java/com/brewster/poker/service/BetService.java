package com.brewster.poker.service;

import com.brewster.poker.bet.Action;
import com.brewster.poker.bet.Bet;
import com.brewster.poker.bet.BetFactory;
import com.brewster.poker.bet.BetFactoryImplementation;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.exception.InvalidBetException;
import com.brewster.poker.exception.UserNotFoundException;
import com.brewster.poker.model.BetEntity;
import com.brewster.poker.model.User;
import com.brewster.poker.player.ComputerPlayer;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.repository.BetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BetService.class);
    private int id;
    private final GameService game;
    private int activePlayersSize;
    private int bigBlind;
    private int smallBlind;
    private int turnNumber;
    private int turnsLeftInRound;
    private List<Player> activeBetters;
    private Player currentBetter;
    private final Integer maxBet;
    private static final Action[] CHECK_ACTIONS = { Action.CHECK, Action.BET, Action.FOLD };
    private static final Action[] CALL_ACTIONS = { Action.CALL, Action.RAISE, Action.FOLD };
    private int pot = 0;
    private int betAmount;
    private final BetFactory betFactory;
    private List<String> betMessages;
    private int bigBlindTurn = -1;
    @Autowired
    BetRepository betRepository;

    public BetService(GameService game, GameSettingsRequest request) {
        this.id = game.getId();
        this.game = game;
//        this.bigBlind = Optional.ofNullable(request.getBigBlind()).orElse(500);
        this.bigBlind = request.getBigBlind();
        this.maxBet = Optional.ofNullable(request.getMaxBet()).map(v -> v == 0 ? Integer.MAX_VALUE : v).orElse(bigBlind * 20);
        betFactory = new BetFactoryImplementation();
        betMessages = new ArrayList<>();
        activeBetters = new ArrayList<>();
    }

    public List<BetEntity> getUserBets(String username){
        Player player = game.getPlayers().stream().filter(v -> v.getDisplayName().equals(username)).findAny()
                .orElseThrow(UserNotFoundException::new);

        UserDto userDto = player.getUser();
        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        return betRepository.findAllByUser(user);
    }

    public void placeBet(BetRequest betRequest){
        Player player = currentBetter;
//        LOGGER.info(player.getDisplayName() + " is placing bet " + betRequest.toString());
        String validationStatement = validateBet(betRequest, player);
        if (validationStatement.isEmpty()) {
            Bet bet = betFactory.createBet(player, betRequest, this);
            String message = bet.process();
            LOGGER.info("Bet has been processed - {}", message);

            betMessages.add(message);
//          betsMade.add(new BetDto(bet, returnStatement));
            adjustTurn();
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
        if (newBetAmount > maxBet){
            validatorError += "This game has a maximum bet of " + maxBet + ". ";
        }

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

    protected BetOptions startNewDeal(){
        pot = 0;
        bigBlindTurn++;
        activeBetters.addAll(game.getPlayers());
        setAllRoundInformation();
        LOGGER.info("starting new deal with " + turnsLeftInRound + " turns");
        return getBetOptions();
    }

    private void setAllRoundInformation(){
        betAmount = 0;
        turnNumber = bigBlindTurn;
        if (turnNumber == activePlayersSize){
            LOGGER.info("not enough players left");
            turnNumber = 0;
        }
        currentBetter = activeBetters.get(turnNumber);
        activePlayersSize = activeBetters.size();
        turnsLeftInRound = activePlayersSize;
        activeBetters.forEach(Player::resetCurrentBetAmount);
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
        BetOptions options = game.getBetOptions();
        LOGGER.info("betManager options = " + options.toString());
        while (options.isBetActive() && options.getPlayer() instanceof ComputerPlayer) {
            ComputerPlayer computerPlayer = (ComputerPlayer) options.getPlayer();
            LOGGER.info("displayName = " + computerPlayer.getDisplayName());
            computerPlayer.placeBet(options, this);
            options = game.getBetOptions();
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

    public void processFold(Player player){
        activeBetters.remove(player);
        turnNumber--;
        updateActivePlayersSize();
    }

    public int getCurrentBettersMoney(){
        return currentBetter.getMoney();
    }

    public Player adjustCurrentPlayer(int turn){
        return activeBetters.get(turn);
    }

    public int getId() {
        return id;
    }

    public GameService getGame() {
        return game;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public int getPot() {
        return pot;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public List<String> getBetMessages() {
        return betMessages;
    }

    public List<Player> getActiveBetters() {
        return activeBetters;
    }

    public void updateActivePlayersSize(){
        this.activePlayersSize = activeBetters.size();
        if (activePlayersSize == 1){
            game.setGameOver();
        }
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

}
