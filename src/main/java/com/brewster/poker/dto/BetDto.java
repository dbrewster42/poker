package com.brewster.poker.dto;

import com.brewster.poker.bet.Action;
import com.brewster.poker.bet.Bet;
import com.brewster.poker.service.BetService;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.player.Player;

public class BetDto {
    protected final int betAmount;
    protected final Player player;
//    protected final BetService betManager;
//    protected final Action chosenAction;
    protected final String betMessage;

    public BetDto(Player player, BetRequest betRequest, BetService betManager, String betMessage){
        this.player = player;
        this.betAmount = betRequest.getBetAmount();
//        this.chosenAction = Action.valueOf(betRequest.getAction());
//        this.betManager = betManager;
        this.betMessage = betMessage;
    }

    public BetDto(Bet bet, String betMessage){
        this.player = bet.getPlayer();
        this.betAmount = bet.getBetAmount();
//        this.chosenAction = bet.getChosenAction();
//        this.betManager = bet.getBetManager();
        this.betMessage = betMessage;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public Player getPlayer() {
        return player;
    }

//    public BetService getBetManager() {
//        return betManager;
//    }
//
//    public Action getChosenAction() {
//        return chosenAction;
//    }

    public String getBetMessage() {
        return betMessage;
    }
}
