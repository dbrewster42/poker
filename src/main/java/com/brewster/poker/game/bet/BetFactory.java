package com.brewster.poker.game.bet;

import com.brewster.poker.game.Player;
import com.brewster.poker.model.request.BetRequest;

interface BetFactory {
    Bet createBet(Player player, BetRequest betRequest);
}
