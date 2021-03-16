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
}