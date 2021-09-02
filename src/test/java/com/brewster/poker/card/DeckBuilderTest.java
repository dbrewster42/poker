package com.brewster.poker.card;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DeckBuilderTest {
    List<Card> standardDeck = DeckBuilder.aDeck().withStandardDeck().build().getCards();

    @Test
    void withStandardDeckIsCorrectSize() {
        assertEquals(52, standardDeck.size());
    }

    @Test
    void withStandardDeckListsAllCardsOnce() {
        Set<Card> uniqueCards = new HashSet<>();
        for (Card card : standardDeck){
            uniqueCards.add(card);
        }
        assertEquals(52, uniqueCards.size());
    }

    @Test
    void cardsHaveCorrectImage(){
        List<Card> unshuffledDeck = DeckBuilder.aDeck().buildStandardDeck();

        assertEquals(unshuffledDeck.get(0).getImage(), "AC.png");
        assertEquals(unshuffledDeck.get(3).getImage(), "AD.png");
        assertEquals(unshuffledDeck.get(4).getImage(), "2C.png");
        assertEquals(unshuffledDeck.get(5).getImage(), "2H.png");
        assertEquals(unshuffledDeck.get(10).getImage(), "3S.png");
    }
}