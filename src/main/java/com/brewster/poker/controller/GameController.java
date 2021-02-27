package com.brewster.poker.controller;

import com.brewster.poker.card.Deck;
import com.brewster.poker.card.DeckBuilder;
import com.brewster.poker.model.response.Response;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/game")
public class GameController {
    private Deck deck;
    private String body;
    private int statusCode = 200;


    @PostMapping("/")
    public Response deal() {
        deck = new DeckBuilder().withStandardDeck().build();
        List<Response> responses = new ArrayList<>();
        return new Response(body, statusCode);
    }

}
