package com.brewster.poker.bet;

import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;

interface BetFactory {
    Bet createBet(Player player, BetRequest betRequest, BetManager betManager);
}
