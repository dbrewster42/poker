package com.brewster.poker.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Objects;

public class Card {
    private final String suit;
    private final String name;
    private final String image;
    @JsonIgnore
    private final int value;

    public Card(String suit, int value, String name, String imagePreFix) {
        this.suit = suit;
        this.value = value;
        this.name = name;
        this.image = imagePreFix + ".png";
    }

    @PersistenceConstructor
    public Card(String suit, String name, int value, String image) {
        this.suit = suit;
        this.value = value;
        this.name = name;
        this.image = image;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value &&
                suit.equals(card.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, value);
    }

    @Override
    public String toString() {
        return name + " of " + suit;
    }
}
