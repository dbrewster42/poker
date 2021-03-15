package com.brewster.poker.game.bet;

import com.brewster.poker.game.Game;
import com.brewster.poker.game.Player;
import com.brewster.poker.model.request.BetRequest;

import java.util.ArrayList;
import java.util.List;

public class BetManager {
    private int id;
    private final Game game;
    private int activePlayers;
    private int bigBlind;
    private int smallBlind;
    private int turn;
    private int turnsLeftInRound;
    private final int limit;
    private static final Action[] CHECK_ACTIONS = { Action.BET, Action.CHECK, Action.FOLD };
    private static final Action[] CALL_ACTIONS = { Action.CALL, Action.RAISE, Action.FOLD };
    private int pot = 0;
    private int betAmount;
    private BetOptions betOptions;
    private final BetFactory betFactory;
    private List<Bet> betsMade;

    public BetManager(Game game, int bigBlind) {
        this.id = game.getId();
        this.game = game;
        this.bigBlind = bigBlind;
        this.smallBlind = bigBlind / 2;
        this.activePlayers = game.getNumberOfPlayers();
        this.turn = 0;
        this.limit = bigBlind * 20;
        betFactory = new BetFactoryImplementation();
        betsMade = new ArrayList<>();
    }
    public BetManager(Game game, int bigBlind, int limit) {
        this.id = game.getId();
        this.game = game;
        this.bigBlind = bigBlind;
        this.smallBlind = bigBlind / 2;
        this.activePlayers = game.getNumberOfPlayers();
        this.turn = 0;
        betFactory = new BetFactoryImplementation();
        betsMade = new ArrayList<>();
        this.limit = limit;
    }

    public String placeBet(BetRequest betRequest){
        Player player = game.getCurrentPlayer();
        String returnStatement = betAmountIsValid(betRequest, player);

        if (returnStatement.isEmpty()){
            Bet bet = betFactory.createBet(player, betRequest, this);
            returnStatement = bet.validate();

            if (returnStatement.isEmpty()){
                returnStatement = bet.process();
                adjustTurn();
            }
        }

        return returnStatement;
    }

    private String betAmountIsValid(BetRequest betRequest, Player player){
        String validatorError = "";
        int newBetAmount = betRequest.getBetAmount();
        if (!betRequest.getUsername().equals(player.getDisplayName())){
            validatorError += "Critical error. The user who placed the bet was not the expected error. ";
        }
        if (newBetAmount > player.getUser().getMoney()){
            validatorError += "You do not have that much money to bet. Until you reload money, your maximum bet is " + player.getUser().getMoney() + ". ";
        }
        if (newBetAmount > limit){
            validatorError += "This game has a maximum bet of " + limit;
        }
        return validatorError;
    }

    protected void adjustTurn(){
        turn++;
        turnsLeftInRound--;
        if (turn == activePlayers){
            turn = 0;
        }
    }
    public void resetTurnsLeft(){
        turnsLeftInRound = activePlayers;
    }

    public void startNextRound(){
        betAmount = 0;
        turn = game.getBigBlindTurn() + 1;
        activePlayers = game.getNumberOfPlayers();
        turnsLeftInRound = activePlayers;
    }

    public BetOptions startNewDeal(Player currentPlayer){
        pot = 0;
        betAmount = 0;
        activePlayers = game.getNumberOfPlayers(); //todo better as param?
        turnsLeftInRound = activePlayers;
        return getBetOptions(currentPlayer);
    }

    public BetOptions getBetOptions(Player currentPlayer){
        //TODO test turns left in round
        if (turnsLeftInRound > 0){
            Action[] actionOptions = getPossibleBetActions(betAmount);
            betOptions = new BetOptions(currentPlayer, actionOptions, betAmount);
            return betOptions;
        } else {
            game.startNextRound();
            return null;
        }
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

    public List<Bet> getBetsMade() {
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
}
