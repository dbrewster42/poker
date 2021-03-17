package com.brewster.poker.cards;

import java.util.List;
import java.util.Random;

public class Deck {
    private List<Card> cards;

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Deck shuffle(){
        for (int count = cards.size() - 1; count > 0; count--){
            Random randomNumber = new Random();
            int otherCard = randomNumber.nextInt(count);
            Card card = cards.get(otherCard);
            cards.set(otherCard, cards.get(count));
            cards.set(count, card);
        }
        return this;
    }
}
