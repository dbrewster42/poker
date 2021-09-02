package com.brewster.poker.bet;

import com.brewster.poker.dto.BetDto;
import com.brewster.poker.exception.InvalidBetException;
import com.brewster.poker.game.Game;
import com.brewster.poker.player.ComputerPlayer;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.request.GameSettingsRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BetManager {
    private int id;
    private final Game game;
    private int activePlayers;
    private int bigBlind;
    private int smallBlind;
    private int turnNumber;
    private int turnsLeftInRound;
    //private int lastBet;
    private final Integer maxBet;
    private static final Action[] CHECK_ACTIONS = { Action.BET, Action.CHECK, Action.FOLD };
    private static final Action[] CALL_ACTIONS = { Action.CALL, Action.RAISE, Action.FOLD };
    private int pot = 0;
    private int betAmount;
    private BetOptions betOptions;
    private final BetFactory betFactory;
    private List<BetDto> betsMade;
//    private List<Bet> betsMade;
    //    private List<String> betMessages;
    private boolean isBet;

    public BetManager(Game game, GameSettingsRequest request) {
        this.id = game.getId();
        this.game = game;
//        this.bigBlind = Optional.ofNullable(request.getBigBlind()).orElse(500);
//        this.bigBlind = request.getBigBlind() * 100;
        this.bigBlind = request.getBigBlind();
        this.smallBlind = bigBlind / 2;
        this.activePlayers = game.getPlayers().size();
        this.turnNumber = 0;
        this.maxBet = Optional.ofNullable(request.getMaxBet()).map(v -> v < 0 ? Integer.MAX_VALUE : v).orElse(bigBlind * 20);
        betFactory = new BetFactoryImplementation();
        betsMade = new ArrayList<>();
    }

    public String placeBet(BetRequest betRequest){
        System.out.println("Placing bet " + betRequest.toString());
        Player player = game.getCurrentPlayer();
        String returnStatement = betAmountIsValid(betRequest, player);
        System.out.println("Is bet amount valid - " + returnStatement);
        if (returnStatement.isEmpty()){
            Bet bet = betFactory.createBet(player, betRequest, this);
            returnStatement = bet.validate();
            System.out.println("Validating bet - " + returnStatement);
            if (returnStatement.isEmpty()){
                returnStatement = bet.process();
                betsMade.add(new BetDto(bet, returnStatement));
                adjustTurn();
            } else {
                throw new InvalidBetException(returnStatement);
            }
        } else {
            throw new InvalidBetException(returnStatement);
        }
        return returnStatement;
    }

    private String betAmountIsValid(BetRequest betRequest, Player player){
        String validatorError = "";
        int newBetAmount = betRequest.getBetAmount();
        if (!betRequest.getUsername().equals(player.getDisplayName())){
            validatorError += "Critical error. The user who placed the bet was not the expected user. ";
        }
        if (newBetAmount > maxBet){
            validatorError += "This game has a maximum bet of " + maxBet;
        }
        if (player.getClass() == HumanPlayer.class ){
            HumanPlayer humanPlayer = (HumanPlayer) player;
            if (newBetAmount > humanPlayer.getMoney()){
                validatorError += "You do not have that much money to bet. Until you reload money, your maximum bet is " + humanPlayer.getMoney() + ". ";
            }
        }
        return validatorError;
    }

    protected void adjustTurn(){
        turnNumber++;
        turnsLeftInRound--;
        if (turnNumber == activePlayers){
            turnNumber = 0;
        }
        if (turnsLeftInRound == 0){
            isBet = false;
        } else {
            game.adjustCurrentPlayer(turnNumber);
        }

    }
    public void resetTurnsLeft(){
        turnsLeftInRound = activePlayers;
    }

    public void startNextRound(){
        betAmount = 0;
        turnNumber = game.getBigBlindTurn() + 1;
        activePlayers = game.getPlayers().size();
        turnsLeftInRound = activePlayers;
        isBet = true;
    }

    public BetOptions startNewDeal(){
        pot = 0;
        betAmount = 0;
        activePlayers = game.getPlayers().size(); //todo better as param?
        turnsLeftInRound = activePlayers;
        System.out.println("starting new deal with " + turnsLeftInRound + " turns");
        return getBetOptions();
    }

    public BetOptions getBetOptions(){
        Player currentPlayer = game.getPlayers().get(turnNumber);
        //TODO test turns left in round
        System.out.println("betManager.getBetOptions " + currentPlayer.getDisplayName() + " turnsLeft = " + turnsLeftInRound);
        if (turnsLeftInRound > 0){
            Action[] actionOptions = getPossibleBetActions(betAmount);
            betOptions = new BetOptions(currentPlayer, actionOptions, betAmount, pot);
            System.out.println("betManager.getBetOptions " + betOptions.toString());
            return betOptions;
        } else {
            game.startNextRound();
            return new BetOptions();
        }
    }

    public BetOptions manageComputerBets(){
        BetOptions options = game.getBetOptions();

        while (options.isBetActive() && options.getPlayer() instanceof ComputerPlayer) {
            System.out.println("displayName = " + options.getPlayer().getDisplayName());
            options.getPlayer().placeBet(game.getRiverCards(), options, game.getBetManager());
            options = game.getBetOptions();
        }

        return options;
    }

    private Action[] getPossibleBetActions(int betAmount){
        if (betAmount > 0){
            return CALL_ACTIONS;
        } else {
            return CHECK_ACTIONS;
        }
    }

    public int getId() {
        return id;
    }

    public Game getGame() {
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

    public List<BetDto> getBetsMade() {
        return betsMade;
    }

    public int getActivePlayers() {
        return activePlayers;
    }

    public void setActivePlayers(int activePlayers) {
        this.activePlayers = activePlayers;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public boolean isBet() {
        return isBet;
    }

    public int getTurnsLeftInRound() {
        return turnsLeftInRound;
    }
}
