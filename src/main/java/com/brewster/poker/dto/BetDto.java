package com.brewster.poker.dto;

import com.brewster.poker.bets.Action;
import com.brewster.poker.bets.Bet;
import com.brewster.poker.bets.BetManager;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.player.Player;

public class BetDto {
    protected final int betAmount;
    protected final Player player;
    protected final BetManager betManager;
    protected final Action chosenAction;
    protected final String betMessage;

    public BetDto(Player player, BetRequest betRequest, BetManager betManager, String betMessage){
        this.player = player;
        this.betAmount = betRequest.getBetAmount();
        this.chosenAction = Action.valueOf(betRequest.getAction());
        this.betManager = betManager;
        this.betMessage = betMessage;
    }

    public BetDto(Bet bet, String betMessage){
        this.player = bet.getPlayer();
        this.betAmount = bet.getBetAmount();
        this.chosenAction = bet.getChosenAction();
        this.betManager = bet.getBetManager();
        this.betMessage = betMessage;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public Player getPlayer() {
        return player;
    }

    public BetManager getBetManager() {
        return betManager;
    }

    public Action getChosenAction() {
        return chosenAction;
    }

    public String getBetMessage() {
        return betMessage;
    }
}
