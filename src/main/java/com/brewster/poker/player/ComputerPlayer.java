package com.brewster.poker.player;

import com.brewster.poker.bet.Action;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.BetManagerEntity;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.service.HandStrengthCalculator;
import com.brewster.poker.model.request.BetRequest;


public class ComputerPlayer extends Player {
    private static int bank = 10000;

    public ComputerPlayer(String displayName, UserDto userDto) {
        super(displayName, userDto);
        setMoney(1000);
    }
    public ComputerPlayer(){}

    public BetRequest placeBet(BetOptions options, BetManagerEntity betManager) {
        int strength = calculateCards();
        System.out.println("strength of cards = " + strength);

        Action primaryAction = options.getPossibleActions()[0];
        BetRequest betRequest;
        if (primaryAction.equals(Action.CHECK)){
            betRequest = createCheckActionsBet(strength, betManager.getBigBlind());
        } else {
            betRequest = createCallActionBet(strength, options, betManager.getBigBlind());
        }
        betRequest.setUsername(options.getName());

        return betRequest;
//        betManager.placeBet(betRequest);
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

    private int calculateCards(){
        int strength = 0;
        int riverCount = getCards().size() - 2;
        if (riverCount == 0){
            strength = HandStrengthCalculator.lookupHoleCards(getCards());
        } else {
            HandStrengthCalculator strengthCalc = new HandStrengthCalculator(getCards());
            strength = strengthCalc.getStrength();
        }

        return strength;
    }

    @Override
    public void joinGame() {

    }

    @Override
    public void leaveGame() {

    }
}
