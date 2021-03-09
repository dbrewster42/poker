package com.brewster.poker.controller;

import com.brewster.poker.card.Card;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.game.Game;
import com.brewster.poker.game.GamesContainer;
import com.brewster.poker.model.request.UserRequest;
import com.brewster.poker.model.request.SettingsRequest;
import com.brewster.poker.model.response.Response;
import com.brewster.poker.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/game")
public class GameController {
    private final UserService userService;
    private UserDto userDto;
    private String body;
    private int statusCode = 200;
    private final ObjectMapper mapper = new ObjectMapper();

    public GameController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public Response createGame(@RequestBody SettingsRequest request) {
        userDto = userService.findPlayer(request.getUsername());
        List<UserDto> players = userService.generateNComputerPlayers(4);
        players.add((userDto));
        Game game = GamesContainer.createGame(players);
        try {
            //body =List<MyClass> myObjects = Arrays.asList(mapper.readValue(json, MyClass[].class))
            body = mapper.writeValueAsString(players);
            //body = mapper.writeValueAsString(playerDto);
        } catch (JsonProcessingException e) {
            body = e.getMessage();
            statusCode = 400;
            e.printStackTrace();
        }
        game.beginNewRound();
        //TODO add bet info- whose turn, available actions, blinds
        System.out.println(game.getId() + " !!!!!!!!!!!!!!!!! " + body);
        Response response = new Response(body, statusCode);
        response.setGameId(game.getId());
        return response;
    }
//    @GetMapping("/{id}")
//    public Response newRound(@PathVariable int id, @RequestBody PlayerRequest request){
//        Game game = GamesContainer.findGameById(id);
//        game.beginNewRound();
//    }

    @PostMapping("/{id}")
    public Response deal(@PathVariable int id, @RequestBody UserRequest request) throws ClassNotFoundException {
        userDto = userService.findPlayer(request.getUsername());
        Game game = GamesContainer.findGameById(id);
        if (game == null){
            throw new ClassNotFoundException("We could not find your game, sorry.");
            //return new Response(body, statusCode);
        }
        List<Card> riverCards = game.dealRiverCardNTimes();
        try {
            body = mapper.writeValueAsString(riverCards);
            body += mapper.writeValueAsString(userDto);
        } catch (JsonProcessingException e) {
            statusCode = 400;
            body = e.getMessage();
            e.printStackTrace();
        }
        return new Response(body, statusCode);
    }

    @PostMapping("/{id}/bet")
    public Response bet(@PathVariable int id, @RequestBody UserRequest request){
        return new Response(body, statusCode);
    }
}
