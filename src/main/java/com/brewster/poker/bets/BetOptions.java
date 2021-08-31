package com.brewster.poker.bets;

import com.brewster.poker.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BetOptions {
    private boolean isBetActive;
    private Action[] possibleActions;
    private int betAmount;
    private int pot;
    @JsonIgnore
    private Player player;
    private String name;

//    public BetOptions(Player player, Action[] possibleActions, int betAmount) {
//        this.player = player;
//        this.possibleActions = possibleActions;
//        this.betAmount = betAmount;
//        this.name = player.getDisplayName();
//    }
    public BetOptions(Player player, Action[] possibleActions, int betAmount, int pot) {
        this.player = player;
        this.possibleActions = possibleActions;
        this.betAmount = betAmount;
        this.name = player.getDisplayName();
        this.pot = pot;
        isBetActive = true;
    }

    public BetOptions(){
        isBetActive = false;
    }

    public String getName() {
        return name;
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

    public int getPot() {
        return pot;
    }

    public boolean isBetActive() {
        return isBetActive;
    }
}
