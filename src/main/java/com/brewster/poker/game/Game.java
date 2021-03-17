package com.brewster.poker.game;

import com.brewster.poker.card.Card;
import com.brewster.poker.card.DeckBuilder;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.game.bet.BetManager;
import com.brewster.poker.game.bet.BetOptions;
import com.brewster.poker.model.request.GameSettingsRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private int id;
    private List<Player> players;
    private Player currentPlayer;
    private List<Card> cards;
    private List<Card> riverCards = new ArrayList<>();
    private int bigBlindTurn = 0;
    private int numberOfPlayers;
    //private int humanPlayers;
    private int openSlots;
    private int desiredNumberOfPlayers;
    private BetManager betManager;

    protected Game(int id, GameSettingsRequest settingsRequest){
        this.id = id;
        this.players = new ArrayList<>();
        this.numberOfPlayers = 0;
        betManager = new BetManager(this, settingsRequest);
        this.desiredNumberOfPlayers = settingsRequest.getNumberOfPlayers();
        this.openSlots = desiredNumberOfPlayers;
        //todo if (request.isCustomRules()){ doSomething() };
    }

    protected Game(int id, HumanPlayer player, GameSettingsRequest settingsRequest){
        this.id = id;
        this.players = new ArrayList<>();
        players.add(player);
        this.numberOfPlayers = players.size();
        betManager = new BetManager(this, settingsRequest);
        this.desiredNumberOfPlayers = settingsRequest.getNumberOfPlayers();
        openSlots = desiredNumberOfPlayers - 1;
        if (settingsRequest.isFillWithComputerPlayers()){
            generateNComputerPlayers(openSlots);
        }
        //todo if (request.isCustomRules()){ doSomething() };
    }

//    public Game(int id, List<HumanPlayer> players, GameSettingsRequest settingsRequest){
//        this.id = id;
//        this.players = players;
//        this.numberOfPlayers = players.size();
//        betManager = new BetManager(this, settingsRequest);
//        this.desiredNumberOfPlayers = settingsRequest.getNumberOfPlayers();
//    }

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
        players.add(player);
        numberOfPlayers = players.size();
        openSlots--;
    }

    public void generateNComputerPlayers(int n){
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            String displayName = "HAL" + random.nextInt(500);
            Player player = new ComputerPlayer(displayName);
            players.add(player);
        }
    }

//    public List<UserDto> generateNComputerPlayers(int numberOfUsers){
//        List<UserDto> users = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < numberOfUsers - 1; i++){
//            UserDto computerUser = new UserDto();
//            computerUser.setUsername("HAL" + random.nextInt(500));
//            computerUser.setMoney(999);
//            computerUser.setPlayer(new HumanPlayer(computerUser.getUsername()));
//            users.add(computerUser);
//        }
//        return users;
//    }

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

    public int getDesiredNumberOfPlayers() {
        return desiredNumberOfPlayers;
    }

    public void setDesiredNumberOfPlayers(int desiredNumberOfPlayers) {
        this.desiredNumberOfPlayers = desiredNumberOfPlayers;
    }
}
