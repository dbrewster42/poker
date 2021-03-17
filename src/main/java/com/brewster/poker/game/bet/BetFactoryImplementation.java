package com.brewster.poker.game.bet;

import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;

public class BetFactoryImplementation implements BetFactory {

    @Override
    public Bet createBet(Player player, BetRequest betRequest, BetManager betManager) {
        switch (betRequest.getAction()){
            case "BET":
                return new BetAction(player, betRequest, betManager);
            case "CALL":
                return new CallAction(player, betRequest, betManager);
            case "CHECK":
                return new CheckAction(player, betRequest, betManager);
            case "FOLD":
                return new FoldAction(player, betRequest, betManager);
            case "RAISE":
                return new RaiseAction(player, betRequest, betManager);
            default:
                return null;
                //TODO throw invalid action exception
        }
    }
}
