package com.brewster.poker.controller;

import com.brewster.poker.card.Card;
import com.brewster.poker.card.Deck;
import com.brewster.poker.card.DeckBuilder;
import com.brewster.poker.game.Game;
import com.brewster.poker.game.GameManager;
import com.brewster.poker.model.response.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

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
    private final ObjectMapper mapper = new ObjectMapper();



    @PostMapping("/{id}")
    public Response deal(@PathVariable int id) {
        Game game = GameManager.findGameById(id);
        List<Card> riverCards = game.getRiverCards();
        try {
            body = mapper.writeValueAsString(riverCards);
            body += mapper.writeValueAsString(game.getPlayers());
        } catch (JsonProcessingException e) {
            statusCode = 400;
            body = e.getMessage();
            e.printStackTrace();
        }
        return new Response(body, statusCode);
    }

}
