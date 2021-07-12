package com.brewster.poker.player;

import com.brewster.poker.cards.Card;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.game.FindBestHand;
import com.brewster.poker.game.PokerHand;
import com.brewster.poker.model.request.BetRequest;

import java.util.List;

public class ComputerPlayer extends Player {
    private static int bank = 10000;
    private FindBestHand findBestHand;

//    public ComputerPlayer(String displayName, UserDto userDto) {
//        super(displayName, userDto);
//    }
    public ComputerPlayer(String displayName, UserDto userDto) {
        super(displayName);
        setUser(userDto);
        setMoney(1000);
    }

    @Override
    public void placeBet(List<Card> riverCards) {
       int strength = calculateCards(riverCards);
        BetRequest betRequest = new BetRequest();
        betRequest.setUsername(this.getDisplayName());
       //TODO where/how is bet placed?
       if (strength > 10){
            //TODO how to set action? Need to have two sets of options depending on whether a call or new bet
       }
    }

    public int calculateCards(List<Card> riverCards){
//        int strength = PokerHand.lookupHand(this.getHand()).getScore();
        int strength = 0;
        int riverCount = riverCards.size();
        if (riverCount == 0){
            strength = PokerHand.lookupHoleCards(getHand());
        } else {
            findBestHand = new FindBestHand(getHand(), riverCards);
            strength = findBestHand.getStrength();
        }
//        else if (riverCount == 5) {
//            strength = PokerHand.lookupHand(this.getHand()).getScore();
//        } else {
//
//            strength =
//        }

        return strength;
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
