package com.brewster.poker.bet;

import com.brewster.poker.model.BetManagerEntity;
import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;

public interface BetFactory {
    Bet createBet(Player player, BetRequest betRequest, BetManagerEntity betManager);
}
