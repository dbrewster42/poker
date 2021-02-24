package com.brewster.poker.card;


import java.util.Random;

public class Deck {
    private Card[] cards;

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public Deck shuffle(){
        for (int count = cards.length - 1; count >= 0; count--){
            Random randomNumber = new Random();
            int otherCard = randomNumber.nextInt(count);
            Card card = cards[otherCard];
            cards[otherCard] = cards[count];
            cards[count] = card;
        }
        return this;
    }

//    public Deck(int wildcards){
//        cards = new Card[52 + wildcards];
//        buildDeckWithJokers(wildcards);
//        shuffle();
//    }
//
//    public Deck(){
//        cards = new Card[52];
//        buildStandardDeck();
//        //buildNewDeck();
//        shuffle();
//    }

//    public List<Card> getAllCards() {
//        return cards;
//    }
//
//    public Deck shuffle(){
//        //int count = cards.size();
//        for (int count = cards.size() - 1; count >= 0; count--){
//            Random randomNumber = new Random();
//            int otherCard = randomNumber.nextInt(count);
//            Card card = cards.get(otherCard);
//            cards.get(count) = cards.get(otherCard);
//        }
//        return this;
//    }

//    public Deck buildNewDeck(){
//        for (int i = 0; i < 13; i++){
//            for (int j = 0; j < 4; j++){
//                Card card = new Card(SUITS[j], VALUES[i], NAMES[i]);
//                cards.add(card);
//            }
//        }
//        return this;
//    }
}
