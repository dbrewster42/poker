package com.brewster.poker.player;

import com.brewster.poker.bet.Action;
import com.brewster.poker.bet.BetManager;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.card.Card;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.game.HandStrengthCalc;
import com.brewster.poker.model.request.BetRequest;

import java.util.List;

public class ComputerPlayer extends Player {
    private static int bank = 10000;

    public ComputerPlayer(String displayName, UserDto userDto) {
        super(displayName);
        setUser(userDto);
        setMoney(1000);
    }

    @Override
    public void placeBet(List<Card> riverCards, BetOptions options, BetManager betManager) {
        int strength = calculateCards(riverCards);
        System.out.println("strength of cards = " + strength);

        Action primaryAction = options.getPossibleActions()[0];
        BetRequest betRequest;
        if (primaryAction.equals(Action.CHECK)){
            betRequest = createCheckActionsBet(strength, betManager.getBigBlind());
        } else {
            betRequest = createCallActionBet(strength, options, betManager.getBigBlind());
        }
        betRequest.setUsername(options.getName());

        String placedBet = betManager.placeBet(betRequest);
        System.out.println("Bet has been placed - " + placedBet);
    }

    private BetRequest createCheckActionsBet(int strength, int bigBlind){
        BetRequest betRequest = new BetRequest();
        if (strength > 4){
            betRequest.setAction(Action.BET.name());
            int betAmount = strength * bigBlind / 5;
            if (betAmount < bank){
                betRequest.setBetAmount(betAmount);
            } else {
                System.out.println("ERROR - COMPUTER RUNNING OUT OF MONEY => " + bank + "$");
                betRequest.setBetAmount(bank);
            }
        } else {
            betRequest.setAction(Action.CHECK.name());
            betRequest.setBetAmount(0);
        }
        return betRequest;
    }

    private BetRequest createCallActionBet(int strength, BetOptions options, int bigBlind){
        BetRequest betRequest = new BetRequest();
        if (strength >= 3){
            if (strength >= 6){
                int betAmount = strength * bigBlind / 5;
                if (betAmount > bank){
                    System.out.println("ERROR - COMPUTER RUNNING OUT OF MONEY => " + bank + "$");
                    betRequest.setBetAmount(bank);
                } else if (betAmount <= options.getBetAmount()){
                    createCallBet(betRequest, options);
                } else {
                    betRequest.setBetAmount(betAmount);
                    betRequest.setAction(Action.RAISE.name());
                }
            } else {
                createCallBet(betRequest, options);
            }
        } else {
            if (options.getBetAmount() > bigBlind){
                betRequest.setAction(Action.FOLD.name());
                betRequest.setBetAmount(0);
            } else {
                createCallBet(betRequest, options);
            }
        }
        return betRequest;
    }

    private void createCallBet(BetRequest betRequest, BetOptions options){
        betRequest.setAction(Action.CALL.name());
        betRequest.setBetAmount(options.getBetAmount());
    }

    public int calculateCards(List<Card> riverCards){
        int strength = 0;
        int riverCount = riverCards.size();
        if (riverCount == 0){
            strength = HandStrengthCalc.lookupHoleCards(getHand());
        } else {
            HandStrengthCalc strengthCalc = new HandStrengthCalc(getHand(), riverCards);
            strength = strengthCalc.getStrength();
        }

        return strength;
    }

//    @Override
//    public void collectWinnings() {
//
//    }

    @Override
    public void joinGame() {

    }

    @Override
    public void leaveGame() {

    }
}
