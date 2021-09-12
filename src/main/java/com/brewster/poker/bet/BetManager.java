package com.brewster.poker.bet;

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

//TODO move this and Game to Service package
public class BetManager {
    private int id;
    private final Game game;
    private int activePlayersSize;
    private int bigBlind;
    private int smallBlind;
    private int turnNumber;
    private int turnsLeftInRound;
    private List<Player> activeBetters;
    private Player currentBetter;
    //private int lastBet;
    private final Integer maxBet;
    private static final Action[] CHECK_ACTIONS = { Action.CHECK, Action.BET, Action.FOLD };
    private static final Action[] CALL_ACTIONS = { Action.CALL, Action.RAISE, Action.FOLD };
    private int pot = 0;
    private int betAmount;
    private BetOptions betOptions;
    private final BetFactory betFactory;
//    private List<BetDto> betsMade;
    private List<String> betMessages;
//    private boolean isBet;
    private int bigBlindTurn = 0;

    public BetManager(Game game, GameSettingsRequest request) {
        this.id = game.getId();
        this.game = game;
//        this.bigBlind = Optional.ofNullable(request.getBigBlind()).orElse(500);
//        this.bigBlind = request.getBigBlind() * 100;
        this.bigBlind = request.getBigBlind();
        this.smallBlind = bigBlind / 2;
//        this.turnNumber = game.getBigBlindTurn() + 1;
//        this.activeBetters = game.getPlayers();
//        this.activePlayersSize = activeBetters.size();
        this.maxBet = Optional.ofNullable(request.getMaxBet()).map(v -> v < 0 ? Integer.MAX_VALUE : v).orElse(bigBlind * 20);
        betFactory = new BetFactoryImplementation();
//        betsMade = new ArrayList<>();
        betMessages = new ArrayList<>();
    }

    public String placeBet(BetRequest betRequest){
//        Player player = game.getCurrentPlayer();
        Player player = currentBetter;
        System.out.println(player.getDisplayName() + " is placing bet " + betRequest.toString());
        String returnStatement = betAmountIsValid(betRequest, player);
        System.out.println("Is bet amount valid - " + returnStatement);
        if (returnStatement.isEmpty()){
            Bet bet = betFactory.createBet(player, betRequest, this);
            returnStatement = bet.validate();
            System.out.println("Validating bet - " + returnStatement);
            if (returnStatement.isEmpty()){
                returnStatement = bet.process();
                System.out.println("Bet has been processed - " + returnStatement);
                betMessages.add(returnStatement);
//                betsMade.add(new BetDto(bet, returnStatement));
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
            validatorError += "This game has a maximum bet of " + maxBet + ". ";
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
        System.out.println("Adjusting turn - " + turnsLeftInRound);
        turnNumber++;
        turnsLeftInRound--;
        if (turnNumber == activePlayersSize){
            turnNumber = 0;
        }
        currentBetter = activeBetters.get(turnNumber);
        if (turnsLeftInRound == 0){
            game.setIsBet(false);
            System.out.println("Round over");
            //TODO could set turnNumber here
        }
    }

    public void resetTurnsLeft(){
        turnsLeftInRound = activePlayersSize;
    }

    public void startNextRound(){
        setAllRoundInformation();
        System.out.println("starting new round with " + currentBetter.getDisplayName());
    }

    public BetOptions startNewDeal(){
        bigBlindTurn++;
        setAllRoundInformation();
        System.out.println("starting new deal with " + turnsLeftInRound + " turns");
        return getBetOptions();
    }

    private void setAllRoundInformation(){
        pot = 0;
        betAmount = 0;
        turnNumber = bigBlindTurn;
        activeBetters = game.getPlayers();
        currentBetter = activeBetters.get(turnNumber);
        activePlayersSize = activeBetters.size();
        turnsLeftInRound = activePlayersSize;
    }

    public BetOptions getBetOptions(){
        System.out.println("betManager.getBetOptions " + currentBetter.getDisplayName() + " turnsLeft = " + turnsLeftInRound + " turnNumber = " + turnNumber);
        if (turnsLeftInRound > 0){
            Action[] actionOptions = getPossibleBetActions(betAmount);
            betOptions = new BetOptions(currentBetter, actionOptions, betAmount, pot);
            return betOptions;
        } else {
            System.out.println("end of betting round");
            game.setIsBet(false);
            //FIXME should we auto call or not? if yes, need logic in game controller for Deal
//            game.startNextRound();
            return new BetOptions();
        }
    }

    public BetOptions manageComputerBets(){
        BetOptions options = game.getBetOptions();
        System.out.println("betManager options = " + options.toString());
        while (options.isBetActive() && options.getPlayer() instanceof ComputerPlayer) {
            System.out.println("displayName = " + options.getPlayer().getDisplayName());
            options.getPlayer().placeBet(game.getRiverCards(), options, game.getBetManager());
            options = game.getBetOptions();
        }
        System.out.println("returning betOptions = " + options.toString());

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

//    public List<BetDto> getBetsMade() {
//        return betsMade;
//    }


    public List<String> getBetMessages() {
        return betMessages;
    }

    public int getActivePlayersSize() {
        return activePlayersSize;
    }

    public void setActivePlayersSize(int activePlayersSize) {
        this.activePlayersSize = activePlayersSize;
    }

    public List<Player> getActiveBetters() {
        return activeBetters;
    }

    public void updateActivePlayersSize(){
        this.activePlayersSize = activeBetters.size();
    }

    public void setActiveBetters(List<Player> activeBetters) {
        this.activeBetters = activeBetters;
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

//    public boolean isBet() {
//        return isBet;
//    }

    public int getTurnsLeftInRound() {
        return turnsLeftInRound;
    }
}
