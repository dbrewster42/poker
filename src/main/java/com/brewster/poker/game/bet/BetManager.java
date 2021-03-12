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
        this.limit = limit;
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
            }
        }

        return returnStatement;
    }
//            Action chosenAction = bet.getChosenAction();
//            if (chosenAction == Action.BET){
//                betAmount = newBetAmount;
//                pot += newBetAmount;
//                turnsLeftInRound = game.getNumberOfPlayers();
//            } else if (chosenAction == Action.CALL){
//                if (newBetAmount == betAmount){
//                    pot += newBetAmount;
//                } else {
//                    returnStatement = "Error. When calling, the bet amount must be the same.";
//                }
//            } else if (){
//
//            }
//            if (newBetAmount > 0){
//                betAmount = newBetAmount;
//                pot += newBetAmount;
//                turnsLeftInRound = game.getNumberOfPlayers();
//            }
//            turnsLeftInRound--;
 //       }

    private String betAmountIsValid(BetRequest betRequest, Player player){
        String validatorError = "";
        int newBetAmount = betRequest.getBetAmount();
        if (!betRequest.getUsername().equals(player.getDisplayName())){
            validatorError += "Critical error. The user who placed the bet was not the expected error. ";
        }
        if (newBetAmount > player.getUser().getMoney()){
            validatorError += "You do not have that much money to bet. Until you reload money, your maximum bet is " + player.getUser().getMoney() + ". ";
        }
        if (newBetAmount < bigBlind){
            validatorError += "The minimum bet is " + bigBlind + ". You may not bet less than the blind";
        }
        if (newBetAmount > limit){
            validatorError += "This game has a maximum bet of " + limit;
        }
        return validatorError;
    }

    private boolean adjustTurn(){
        turn++;
        turnsLeftInRound--;
        if (turnsLeftInRound == 0){
            //TODO is 0? or < 0?   || should I adjust the numbers before or after?
            return false;
        }
        if (turn == game.getNumberOfPlayers()){
            turn = 0;
        }
        return true;
    }

    public BetOptions startNewDeal(){
        pot = 0;
        betAmount = 0;
        activePlayers = game.getNumberOfPlayers();
        turnsLeftInRound = activePlayers;
        return getBetOptions();
    }

    public void startNextRound(){
        betAmount = 0;
        turnsLeftInRound = activePlayers;
    }

    public BetOptions getBetOptions(){
        Player playerUp = game.getPlayers().get(turn);
        if (adjustTurn()){
            Action[] actionOptions = getPossibleBetActions(betAmount);
            betOptions = new BetOptions(playerUp, actionOptions, betAmount);
            return betOptions;
        } else {
            startNextRound();
            return null;
            //FIXME how to dictate flow if round is over?
        }
    }

    private Action[] getPossibleBetActions(int betAmount){
        if (betAmount > 0){
            return CALL_ACTIONS;
        } else {
            return CHECK_ACTIONS;
        }
    }
//    public List<Action> getPossibleBetActions(int betAmount){
//        possibleActions = new ArrayList<>();
//        possibleActions.add(Action.FOLD);
//        if (betAmount > 0){
//            possibleActions.add(Action.CALL);
//            possibleActions.add(Action.RAISE);
//        } else {
//            possibleActions.add(Action.BET);
//            possibleActions.add(Action.CHECK);
//        }
//        return possibleActions;
//    }


}
