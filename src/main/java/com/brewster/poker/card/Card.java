package com.brewster.poker.card;

public class Card {
    final private String suit;
    final private int value;
    final private String name;

    public Card(String suit, int value, String name) {
        this.suit = suit;
        this.value = value;
        this.name = name;
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
}
