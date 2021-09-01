package com.brewster.poker.player;

import com.brewster.poker.bets.Action;
import com.brewster.poker.bets.BetManager;
import com.brewster.poker.bets.BetOptions;
import com.brewster.poker.cards.Card;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.game.FindBestHand;
import com.brewster.poker.game.PokerHand;
import com.brewster.poker.model.request.BetRequest;

import java.util.List;

public class ComputerPlayer extends Player {
    private static int bank = 10000;
    private FindBestHand findBestHand;

//    public ComputerPlayer(String displayName, UserDto userDto) {
//        super(displayName, userDto);
//    }
    public ComputerPlayer(String displayName, UserDto userDto) {
        super(displayName);
        setUser(userDto);
        setMoney(1000);
    }

    @Override
    public void placeBet(List<Card> riverCards, BetOptions options, BetManager betManager) {
        System.out.println("placing bet");
        int strength = calculateCards(riverCards);
        System.out.println("strength of cards = " + strength);

//        Action[] possibleActions = options.getPossibleActions();
        Action primaryAction = options.getPossibleActions()[0];
        BetRequest betRequest;
        if (primaryAction.equals(Action.BET)){
            betRequest = createCheckActionsBet(strength, betManager.getBigBlind());
        } else {
            betRequest = createCallActionBet(strength, options, betManager.getBigBlind());
        }
        betRequest.setUsername(options.getName());

        String placedBet = betManager.placeBet(betRequest);
        System.out.println("Bet has been placed - " + placedBet);
//        if (!isValid.isBlank()){
//            System.out.println("Problem with bet " + isValid);
//            fold(options);
//        }
    }

    private BetRequest fold(BetOptions options){
        BetRequest betRequest = new BetRequest();
        betRequest.setAction(Action.FOLD.name());
        betRequest.setUsername(options.getName());
        betRequest.setBetAmount(0);
        return betRequest;
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
        if (strength > 3){
            if (strength > 6){
                int betAmount = strength * bigBlind / 5;
                if (betAmount > bank){
                    System.out.println("ERROR - COMPUTER RUNNING OUT OF MONEY => " + bank + "$");
                    betRequest.setBetAmount(bank);
                } else if (betAmount < options.getBetAmount()){
                    checkTurn(betRequest, options);
                } else {
                    betRequest.setBetAmount(betAmount);
                    betRequest.setAction(Action.RAISE.name());
                }
            } else {
                checkTurn(betRequest, options);
            }
        } else {
            betRequest.setAction(Action.FOLD.name());
            betRequest.setBetAmount(0);
        }
        return betRequest;
    }

    private void checkTurn(BetRequest betRequest, BetOptions options){
        betRequest.setAction(Action.CHECK.name());
        betRequest.setBetAmount(options.getBetAmount());
    }

    public int calculateCards(List<Card> riverCards){
        int strength = 0;
        int riverCount = riverCards.size();
        if (riverCount == 0){
            strength = PokerHand.lookupHoleCards(getHand());
        } else {
            findBestHand = new FindBestHand(getHand(), riverCards);
            strength = findBestHand.getStrength();
        }

        return strength;
    }

    @Override
    public void collectWinnings() {

    }

    @Override
    public void joinGame() {

    }

    @Override
    public void leaveGame() {

    }
}
