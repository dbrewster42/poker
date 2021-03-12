package com.brewster.poker.game.bet.action;

import com.brewster.poker.game.Player;
import com.brewster.poker.game.bet.Bet;
import com.brewster.poker.model.request.BetRequest;

public class FoldAction extends Bet {
    public FoldAction(Player player, BetRequest betRequest) {
        super(player, betRequest);
    }

    @Override
    public String validate() {
        return null;
    }

    @Override
    public String process() {
        return null;
    }
}
