package com.brewster.poker.game.bet;

import com.brewster.poker.game.Player;
import com.brewster.poker.game.bet.action.*;
import com.brewster.poker.model.request.BetRequest;

public class BetFactoryImplementation implements BetFactory {

    @Override
    public Bet createBet(Player player, BetRequest betRequest) {
        switch (betRequest.getAction()){
            case "BET":
                return new BetAction(player, betRequest);
            case "CALL":
                return new CallAction(player, betRequest);
            case "CHECK":
                return new CheckAction(player, betRequest);
            case "FOLD":
                return new FoldAction(player, betRequest);
            case "RAISE":
                return new RaiseAction(player, betRequest);
            default:
                return null;
                //TODO throw invalid action exception
        }
    }
}
