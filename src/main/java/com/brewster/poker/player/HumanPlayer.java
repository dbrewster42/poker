package com.brewster.poker.player;

import com.brewster.poker.bets.BetManager;
import com.brewster.poker.bets.BetOptions;
import com.brewster.poker.cards.Card;
import com.brewster.poker.dto.UserDto;

import java.util.List;

public class HumanPlayer extends Player {

    public HumanPlayer(String displayName, UserDto user) {
        super(displayName, user);
    }

    @Override
    public void placeBet(List<Card> riverCards, BetOptions options, BetManager betManager) {

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
