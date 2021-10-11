package com.brewster.poker.bet;

import com.brewster.poker.model.BetManagerEntity;
import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.service.BetService;
import org.springframework.stereotype.Component;

@Component
public class BetFactoryImplementation implements BetFactory {

    @Override
    public Bet createBet(Player player, BetRequest betRequest, BetManagerEntity betManager) {
        switch (betRequest.getAction()){
            case "BET":
                return new BetAction(player, betRequest.getBetAmount(), betManager);
            case "CALL":
                return new CallAction(player, betRequest.getBetAmount(), betManager);
            case "CHECK":
                return new CheckAction(player, betRequest.getBetAmount(), betManager);
            case "FOLD":
                return new FoldAction(player, betRequest.getBetAmount(), betManager);
            case "RAISE":
                return new RaiseAction(player, betRequest.getBetAmount(), betManager);
            default:
                throw new IllegalArgumentException("That action is not valid");
        }
    }
}
