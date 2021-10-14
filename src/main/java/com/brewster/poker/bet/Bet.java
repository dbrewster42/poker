package com.brewster.poker.bet;

import com.brewster.poker.player.Player;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.BetManagerEntity;
import org.springframework.data.annotation.Transient;

public abstract class Bet {
    protected int betAmount = 0;
    protected Player player;
    protected String chosenAction;
    @Transient
    protected BetManagerEntity betManager;
    protected String message;

    public Bet(Player player, BetRequest betRequest, BetManagerEntity betManager){
        this.player = player;
        this.chosenAction = betRequest.getAction();
        this.betAmount = betRequest.getBetAmount();
        this.betManager = betManager;
    }
    public Bet(){}

    public Bet(Player player, int betAmount, String chosenAction, BetManagerEntity betManager){
        this.player = player;
        this.chosenAction = chosenAction;
        this.betAmount = betAmount;
        this.betManager = betManager;
    }

    public abstract String process();

    public Player getPlayer() {
        return player;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public String getChosenAction() {
        return chosenAction;
    }

    public BetManagerEntity getBetManager() {
        return betManager;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setChosenAction(String chosenAction) {
        this.chosenAction = chosenAction;
    }

    public void setBetManager(BetManagerEntity betManager) {
        this.betManager = betManager;
    }
}
