package com.brewster.poker.game;

import com.brewster.poker.card.Card;
import com.brewster.poker.card.DeckBuilder;
import com.brewster.poker.game.bet.BetManager;
import com.brewster.poker.game.bet.BetOptions;
import com.brewster.poker.model.request.GameSettingsRequest;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int id;
    private List<Player> players;
    private Player currentPlayer;
    private List<Card> cards;
    private List<Card> riverCards = new ArrayList<>();
    private int bigBlindTurn = 0;
    private int openSlots;
    private int desiredNumberOfPlayers;
    private BetManager betManager;

    protected Game(int id, HumanPlayer player, GameSettingsRequest settingsRequest){
        this.id = id;
        this.players = new ArrayList<>();
        players.add(player);
        betManager = new BetManager(this, settingsRequest);
        this.desiredNumberOfPlayers = settingsRequest.getNumberOfPlayers();
        openSlots = desiredNumberOfPlayers - 1;
        //todo if (request.isCustomRules()){ doSomething() };
    }
    protected Game(int id, List<Player> players, GameSettingsRequest settingsRequest){
        this.id = id;
        this.players = players;
        betManager = new BetManager(this, settingsRequest);
        this.desiredNumberOfPlayers = settingsRequest.getNumberOfPlayers();
        openSlots = desiredNumberOfPlayers - 1;
        //todo if (request.isCustomRules()){ doSomething() };
    }

    public BetOptions startNewDeal(){
        cards = getNewStandardDeck();
        dealPlayerCards();
        currentPlayer = players.get(bigBlindTurn + 1);
        return betManager.startNewDeal(currentPlayer);
    }

    public BetOptions getBetOptions(){
        return betManager.getBetOptions(currentPlayer);
    }

    public List<Card> startNextRound(){
        betManager.startNextRound();
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
        return DeckBuilder.aDeck().withStandardDeck().build().getCards();
    }

    private void dealPlayerCards(){
        for (int i = 0; i < 2; i++){
            for (Player player : players){
                player.dealCard(cards.get(0));
                cards.remove(0);
            }
        }
    }

    public void addPlayerToGame(HumanPlayer player){
        if (desiredNumberOfPlayers == players.size()){
            for (Player eachPlayer : players){
                if (eachPlayer.getClass() == ComputerPlayer.class){
                    eachPlayer = player;
                }
            }
        } else {
            players.add(player);
        }
        openSlots--;

    }
    public void addPlayerToGame(ComputerPlayer player){
        players.add(player);
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

    public int getBigBlindTurn() {
        return bigBlindTurn;
    }

    public void setBigBlindTurn(int bigBlindTurn) {
        this.bigBlindTurn = bigBlindTurn;
    }

    public int getOpenSlots() {
        return openSlots;
    }

    public void setOpenSlots(int openSlots) {
        this.openSlots = openSlots;
    }
}
