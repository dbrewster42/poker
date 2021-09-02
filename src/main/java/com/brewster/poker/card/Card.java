package com.brewster.poker.card;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Card {
    final private String suit;
    @JsonIgnore
    final private int value;
    final private String name;
    final private String image;

    public Card(String suit, int value, String name, String imagePreFix) {
        this.suit = suit;
        this.value = value;
        this.name = name;
        this.image = imagePreFix + ".png";
    }

    public void show(){
        System.out.println("The " + name + " of " + suit);
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
}
