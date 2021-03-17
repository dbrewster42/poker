package com.brewster.poker.game.bet;

import com.brewster.poker.player.Player;

public class BetOptions {
    private Action[] possibleActions;
    private int betAmount;
    private Player player;

    public BetOptions(Player player, Action[] possibleActions, int betAmount) {
        this.player = player;
        this.possibleActions = possibleActions;
        this.betAmount = betAmount;
    }

    public Player getPlayer() {
        return player;
    }

    public Action[] getPossibleActions() {
        return possibleActions;
    }

    public int getBetAmount() {
        return betAmount;
    }
}
