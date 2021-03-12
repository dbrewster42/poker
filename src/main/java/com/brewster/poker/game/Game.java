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
    private int bigBlindTurn = 0;
    private Player currentPlayer;
    private int numberOfPlayers;
    private int id;
    private BetManager betManager;

    public Game(int id, List<Player> players, SettingsRequest settingsRequest){
        this.id = id;
        this.players = players;
        this.numberOfPlayers = players.size();
        betManager = new BetManager(this, settingsRequest.getBigBlind());
    }

    public BetOptions startNewDeal(){
        cards = getNewStandardDeck();
        dealPlayerCards();
        currentPlayer = players.get(bigBlindTurn);
        return betManager.startNewDeal();
        //return betManager.getBetOptions(0);
        //return betManager.getPossibleBetActions(0);
    }

    public List<Card> startNextRound(){
        int count = 1;
        if (riverCards.size() == 0){
            count = 3;
        }
        return dealRiverCardNTimes(count);
    }

    public List<Card> dealRiverCardNTimes(int count){
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
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

    public int getBigBlindTurn() {
        return bigBlindTurn;
    }

    public void setBigBlindTurn(int bigBlindTurn) {
        this.bigBlindTurn = bigBlindTurn;
    }
//    public List<Card> getCards() {
//        return cards;
//    }
//
//    public void setCards(List<Card> cards) {
//        this.cards = cards;
//    }
}
