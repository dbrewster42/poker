package com.brewster.poker.game.bet;

import com.brewster.poker.game.HumanPlayer;
import com.brewster.poker.model.request.BetRequest;

interface BetFactory {
    Bet createBet(HumanPlayer player, BetRequest betRequest, BetManager betManager);
}
