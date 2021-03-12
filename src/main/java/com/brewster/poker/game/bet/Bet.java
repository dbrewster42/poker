package com.brewster.poker.game.bet;


import com.brewster.poker.game.Player;
import com.brewster.poker.model.request.BetRequest;

public class Bet {
    private int betAmount;
    private Action chosenAction;
    private Player player;

    public Bet(String action, int betAmount, Player player) {
        this.chosenAction = Action.valueOf(action);
        this.betAmount = betAmount;
        this.player = player;
    }

    public Bet(Player player, BetRequest betRequest){
        this.player = player;
        this.betAmount = betRequest.getBetAmount();
        this.chosenAction = betRequest.getAction();
    }

    public void process(){
        //TODO adjust BetManager depending on type.
    }

    public Player getPlayer() {
        return player;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public Action getChosenAction() {
        return chosenAction;
    }
}
