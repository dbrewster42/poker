package com.brewster.poker.game.bet;

import com.brewster.poker.game.Game;
import com.brewster.poker.game.Player;
import com.brewster.poker.model.request.BetRequest;

public class BetManager {
    private Game game;
    private int activePlayers;
    private int bigBlind;
    private int smallBlind;
    private int turn;
    private int betsLeft;
    private int limit = 1000;
    private Action[] checkActions = { Action.BET, Action.CHECK, Action.FOLD };
    private Action[] callActions = { Action.CALL, Action.RAISE, Action.FOLD };
    private int pot = 0;
    private int betAmount;
    private BetOptions betOptions;

    public BetManager(Game game, int bigBlind) {
        this.game = game;
        this.bigBlind = bigBlind;
        this.smallBlind = bigBlind / 2;
        this.activePlayers = game.getNumberOfPlayers();
        this.turn = 0;
    }
    public BetManager(Game game, int bigBlind, int limit) {
        this.game = game;
        this.bigBlind = bigBlind;
        this.smallBlind = bigBlind / 2;
        this.activePlayers = game.getNumberOfPlayers();
        this.turn = 0;
        this.limit = limit;
    }

    public String makeBet(BetRequest betRequest){
        int newBetAmount = betRequest.getBetAmount();
        Player player = game.getCurrentPlayer();
        String returnStatement = betIsValid(betRequest, player);
        if (returnStatement.isEmpty()){
            Bet bet = new Bet(player, betRequest);
            Action chosenAction = bet.getChosenAction();
            if (chosenAction == Action.BET){
                betAmount = newBetAmount;
                pot += newBetAmount;
                betsLeft = game.getNumberOfPlayers();
            } else if (chosenAction == Action.CALL){
                if (newBetAmount == betAmount){
                    pot += newBetAmount;
                } else {
                    returnStatement = "Error. When calling, the bet amount must be the same.";
                }
            } else if (){

            }
            //TODO abstract factor of bets??!
//            if (newBetAmount > 0){
//                betAmount = newBetAmount;
//                pot += newBetAmount;
//                betsLeft = game.getNumberOfPlayers();
//            }
            betsLeft--;
        }
        return returnStatement;
        //TODO finish function. do we need a bet class since we have bet request?
    }
    private String betIsValid(BetRequest betRequest, Player player){
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
        //TODO validation based on action
        return validatorError;
    }

    private BetOptions adjustTurn(){
        turn++;
        if (turn == game.getNumberOfPlayers()){
            turn = 0;
        }
        return getBetOptions();
    }

    public BetOptions startNewRound(){
        pot = 0;
        betAmount = 0;
        return getBetOptions();
    }

    public BetOptions getBetOptions(){
        Player playerUp = game.getPlayers().get(turn);
        adjustTurn();
        Action[] actionOptions = getPossibleBetActions(betAmount);
        betOptions = new BetOptions(playerUp, actionOptions, betAmount);
        return betOptions;
    }

    private Action[] getPossibleBetActions(int betAmount){
        if (betAmount > 0){
            return callActions;
        } else {
            return checkActions;
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
