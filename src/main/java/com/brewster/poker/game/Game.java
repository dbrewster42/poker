package com.brewster.poker.game;

import com.brewster.poker.card.Card;
import com.brewster.poker.card.DeckBuilder;
import com.brewster.poker.bet.BetManager;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.response.GameResponse;
import com.brewster.poker.player.ComputerPlayer;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int id;
    private List<Player> players;
    private Player currentPlayer;
    private List<Card> cards;
    private List<Card> riverCards = new ArrayList<>();
    private int bigBlindTurn = -1;
    private int openSlots;
    private int desiredNumberOfPlayers;
    private BetManager betManager;
    private boolean isLastRound;

    protected Game(int id, HumanPlayer player, GameSettingsRequest settingsRequest){
        this.id = id;
        this.players = new ArrayList<>();
        players.add(player);
        betManager = new BetManager(this, settingsRequest);
        this.desiredNumberOfPlayers = settingsRequest.getNumberOfPlayers();
        openSlots = desiredNumberOfPlayers - 1;
    }
    protected Game(int id, List<Player> players, GameSettingsRequest settingsRequest){
        this.id = id;
        this.players = players;
        betManager = new BetManager(this, settingsRequest);
        this.desiredNumberOfPlayers = settingsRequest.getNumberOfPlayers();
        openSlots = desiredNumberOfPlayers - 1;
        //todo if (request.isCustomRules()){ doSomething() };
    }
    //TODO is placeBet a good practice?
    public String placeBet(BetRequest betRequest){
        return betManager.placeBet(betRequest);
    }

    public BetOptions startNewDeal(){
        cards = getNewStandardDeck();
        dealPlayerCards();
        currentPlayer = players.get(bigBlindTurn + 1);
        return betManager.startNewDeal();
    }

    public BetOptions getBetOptions(){
//        System.out.println("getting betOptions for " + currentPlayer.getDisplayName());
//        System.out.println("turn number " + players.indexOf(currentPlayer));
        return betManager.getBetOptions();
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
    public GameResponse getGameResponse(){
        List<UserDto> users = new ArrayList<>();
        for (Player player : players){
            users.add(player.getUser());
        }
        GameResponse gameResponse = new GameResponse(users, bigBlindTurn, betManager.getTurnNumber());
        return gameResponse;
    }
    public List<UserDto> getUsers(){
        List<UserDto> users = new ArrayList<>();
        for (Player player : players){
            player.getUser().setDisplayName(player.getDisplayName());
            users.add(player.getUser());
        }
       return users;
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

    public void adjustCurrentPlayer(int turn){
        currentPlayer = players.get(turn);
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

    public BetManager getBetManager() {
        return betManager;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", players=" + players +
                '}';
    }
}
