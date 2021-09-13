package com.brewster.poker.bet;

import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.service.BetService;

public interface BetFactory {
    Bet createBet(Player player, BetRequest betRequest, BetService betManager);
}
