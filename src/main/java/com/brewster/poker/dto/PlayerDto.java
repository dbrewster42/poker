package com.brewster.poker.dto;

import com.brewster.poker.card.Card;

import java.util.List;

public class PlayerDto {
    private Integer id;
    private String name;
    private int money;
    private List<Card> hand;
}
