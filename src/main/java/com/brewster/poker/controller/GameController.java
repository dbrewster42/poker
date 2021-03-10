package com.brewster.poker.controller;

import com.brewster.poker.card.Card;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.game.Game;
import com.brewster.poker.game.GamesContainer;
import com.brewster.poker.game.bet.BetOptions;
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
        userDto = userService.findUser(request.getUsername());
        List<UserDto> users = userService.generateNComputerUsers(4);
        users.add((userDto));
        Game game = GamesContainer.createGame(users, request);
        try {
            //body =List<MyClass> myObjects = Arrays.asList(mapper.readValue(json, MyClass[].class))
            body = mapper.writeValueAsString(users);
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
    public Response deal(@PathVariable int id, @RequestBody UserRequest request) throws ClassNotFoundException {
        userDto = userService.findUser(request.getUsername());
        Game game = GamesContainer.findGameById(id);
        if (game == null){
            throw new ClassNotFoundException("We could not find your game, sorry.");
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

    @GetMapping("/{id}/bet")
    public Response startNewRound(@PathVariable int id){
        Game game = GamesContainer.findGameById(id);
        BetOptions options = game.beginNewRound();
        try {
            body = mapper.writeValueAsString(options);
        } catch (JsonProcessingException e) {
            body = e.getMessage();
            statusCode = 400;
            e.printStackTrace();
        }
        return new Response(body, statusCode);
    }

    @PostMapping("/{id}/bet")
    public Response bet(@PathVariable int id, @RequestBody UserRequest request){
        return new Response(body, statusCode);
    }

}
