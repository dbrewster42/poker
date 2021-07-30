package com.brewster.poker.bets;

import com.brewster.poker.dto.BetDto;
import com.brewster.poker.game.Game;
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
    private int turn;
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
        this.bigBlind = request.getBigBlind() * 100;
        this.smallBlind = bigBlind / 2;
        this.activePlayers = game.getPlayers().size();
        this.turn = 0;
        this.maxBet = Optional.ofNullable(request.getMaxBet()).map(v -> v < 0 ? Integer.MAX_VALUE : v).orElse(bigBlind * 20);
        betFactory = new BetFactoryImplementation();
        betsMade = new ArrayList<>();
    }

    public String placeBet(BetRequest betRequest){
        Player player = game.getCurrentPlayer();
        String returnStatement = betAmountIsValid(betRequest, player);

        if (returnStatement.isEmpty()){
            Bet bet = betFactory.createBet(player, betRequest, this);
            returnStatement = bet.validate();

            if (returnStatement.isEmpty()){
                returnStatement = bet.process();
                betsMade.add(new BetDto(bet, returnStatement));
                adjustTurn();
            }
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
        turn++;
        turnsLeftInRound--;
        if (turn == activePlayers){
            turn = 0;
        }
        if (turnsLeftInRound == 0){
            isBet = false;
        }
    }
    public void resetTurnsLeft(){
        turnsLeftInRound = activePlayers;
    }

    public void startNextRound(){
        betAmount = 0;
        turn = game.getBigBlindTurn() + 1;
        activePlayers = game.getPlayers().size();
        turnsLeftInRound = activePlayers;
        isBet = true;
    }

    public BetOptions startNewDeal(Player currentPlayer){
        pot = 0;
        betAmount = 0;
        activePlayers = game.getPlayers().size(); //todo better as param?
        turnsLeftInRound = activePlayers;
        return getBetOptions(currentPlayer);
    }

    public BetOptions getBetOptions(Player currentPlayer){
        //TODO test turns left in round
        if (turnsLeftInRound > 0){
            Action[] actionOptions = getPossibleBetActions(betAmount);
            betOptions = new BetOptions(currentPlayer, actionOptions, betAmount, pot);
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

    public int getTurn() {
        return turn;
    }

    public boolean isBet() {
        return isBet;
    }
}
