package com.brewster.poker.controller;

import com.brewster.poker.card.Card;
import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.game.Game;
import com.brewster.poker.game.GamesContainer;
import com.brewster.poker.model.request.PlayerRequest;
import com.brewster.poker.model.request.SettingsRequest;
import com.brewster.poker.model.response.Response;
import com.brewster.poker.service.PlayerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/game")
public class GameController {
    private final PlayerService playerService;
    private PlayerDto playerDto;
    private String body;
    private int statusCode = 200;
    private final ObjectMapper mapper = new ObjectMapper();

    public GameController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/start")
    public Response startGame(@RequestBody SettingsRequest request) {
        playerDto = playerService.findPlayer(request.getUsername());
        List<PlayerDto> players = playerService.gatherPlayersForGame(playerDto, 4);
        Game game = GamesContainer.createGame(players);
        try {
            body = mapper.writeValueAsString(players);
        } catch (JsonProcessingException e) {
            body = e.getMessage();
            statusCode = 400;
            e.printStackTrace();
        }
        System.out.println(game.getId() + " !!!!!!!!!!!!!!!!! " + body);
        Response response = new Response(body, statusCode);
        response.setGameId(game.getId());
        return response;
    }

    @PostMapping("/{id}")
    public Response deal(@PathVariable int id, @RequestBody PlayerRequest request) throws ClassNotFoundException {
        playerDto = playerService.findPlayer(request.getUsername());
        Game game = GamesContainer.findGameById(id);
        if (game == null){
            throw new ClassNotFoundException("We could not find your game, sorry.");
            //return new Response(body, statusCode);
        }
        List<Card> riverCards = game.getRiverCards();
        try {
            body = mapper.writeValueAsString(riverCards);
            body += mapper.writeValueAsString(playerDto);
        } catch (JsonProcessingException e) {
            statusCode = 400;
            body = e.getMessage();
            e.printStackTrace();
        }
        return new Response(body, statusCode);
    }

    @PostMapping("/{id}/bet")
    public Response bet(@PathVariable int id, @RequestBody PlayerRequest request){
        return new Response(body, statusCode);
    }
}
