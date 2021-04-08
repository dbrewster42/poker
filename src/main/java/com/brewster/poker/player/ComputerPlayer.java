package com.brewster.poker.player;

import com.brewster.poker.cards.Card;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.game.PokerHand;

import java.util.List;

public class ComputerPlayer extends Player {
    private static int bank = 10000;

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
    }

    public int calculateCards(List<Card> riverCards){
//        int strength = PokerHand.lookupHand(this.getHand()).getScore();
        int strength = 0;
        int riverCount = riverCards.size();
        if (riverCount == 0){
            strength = PokerHand.lookupHoleCards(getHand());
        } else if (riverCount == 5) {
            //FIXME this won't work. it needs the 5 best cards
            strength = PokerHand.lookupHand(this.getHand()).getScore();
        } else {
            strength =
        }

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
