package com.brewster.poker.game;

import com.brewster.poker.card.Card;
import com.brewster.poker.card.Deck;
import com.brewster.poker.card.DeckBuilder;
import com.brewster.poker.dto.PlayerDto;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Deck deck;
    private List<Card> cards;
    private List<Card> riverCards;
    private List<PlayerDto> players;
    private int numberOfPlayers;
    private int id;

    public Game(int id, List<PlayerDto> players){
        this.id = id;
        this.players = players;
        this.numberOfPlayers = players.size();
    }

    public List<Card> deal(){
        //deck = new DeckBuilder().withStandardDeck().build();
        cards = new DeckBuilder().withStandardDeck().build().getCards();
//        int i = 0;
//        for (PlayerDto player : players){
//            List<Card> holeCards = List.of(cards[i], cards[i + numberOfPlayers]);
//            i++;
//        }
        for (int i = 0; i < 2; i++){
            for (PlayerDto player : players){
                player.dealCard(cards.get(0));
                cards.remove(0);
            }
        }

        cards.remove(0);
        for (int i = 0; i < 3; i++){
            riverCards.add(cards.get(0));
            cards.remove(0);
        }

        return riverCards;
    }

    public int getId() {
        return id;
    }

    public List<Card> getRiverCards() {
        return riverCards;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<PlayerDto> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDto> players) {
        this.players = players;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    //    public List<Response> dealNewGame(List<Player> players){
//
//    }
}
