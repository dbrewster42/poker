package com.brewster.poker.game;

import com.brewster.poker.card.Card;
import com.brewster.poker.card.DeckBuilder;
import com.brewster.poker.game.bet.BetManager;
import com.brewster.poker.game.bet.BetOptions;
import com.brewster.poker.model.request.SettingsRequest;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Card> cards;
    private List<Card> riverCards = new ArrayList<>();
    private List<Player> players;
    private int numberOfPlayers;
    private int id;
    private BetManager betManager;

    public Game(int id, List<Player> players, SettingsRequest settingsRequest){
        this.id = id;
        this.players = players;
        this.numberOfPlayers = players.size();
        betManager = new BetManager(this, settingsRequest.getBigBlind());
    }

    public BetOptions beginNewRound(){
        cards = getNewStandardDeck();
        dealPlayerCards();
        return betManager.getBetOptions(0);
        //return betManager.getPossibleBetActions(0);
    }


//    public List<Player> beginNewRound(){
//        cards = getNewStandardDeck();
//        dealPlayerCards();
//        return players;
//    }

    public List<Card> dealRiverCardNTimes(){
        int count = 1;
        if (riverCards.size() == 0){
            count = 3;
        }
        cards.remove(0);
        for (int i = 0; i < count; i++){
            riverCards.add(cards.get(0));
            cards.remove(0);
        }
        return riverCards;
    }
    private List<Card> getNewStandardDeck(){
        return new DeckBuilder().withStandardDeck().build().getCards();
    }

    //private List<PlayerDto> dealPlayerCards(){
    private void dealPlayerCards(){
        for (int i = 0; i < 2; i++){
            for (Player player : players){
                player.dealCard(cards.get(0));
                cards.remove(0);
            }
        }
        //return players;
    }

    public int getId() {
        return id;
    }

    public List<Card> getRiverCards() {
        return riverCards;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    //    public List<Card> getCards() {
//        return cards;
//    }
//
//    public void setCards(List<Card> cards) {
//        this.cards = cards;
//    }
}
