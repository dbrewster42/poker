package com.brewster.poker.controller;

import com.brewster.poker.card.Card;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.game.Game;
import com.brewster.poker.game.GamesContainer;
import com.brewster.poker.game.bet.BetOptions;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.request.JoinRequest;
import com.brewster.poker.model.request.UserRequest;
import com.brewster.poker.model.request.GameSettingsRequest;
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
    private Game game;
    private final UserService userService;
    private UserDto userDto;
    private String body;
    private int statusCode = 200;
    private final ObjectMapper mapper = new ObjectMapper();

    public GameController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public Response createGame(@RequestBody GameSettingsRequest request) {
        userDto = userService.findUser(request.getUsername());
//        List<UserDto> users = userService.generateNComputerUsers(4); //todo move to game. we don't need users, just players
//        users.add((userDto));
//        game = GamesContainer.createGame(users, request);
        game = GamesContainer.createGame(userDto, request);
        try {
            //body =List<MyClass> myObjects = Arrays.asList(mapper.readValue(json, MyClass[].class))
            body = mapper.writeValueAsString(game);
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

    @GetMapping("/join")
    public Response findGame() {
        List<Game> openGames = GamesContainer.findAvailableGames();
        try {
            body = mapper.writeValueAsString(openGames);
        } catch (JsonProcessingException e) {
            body = e.getMessage();
            statusCode = 400;
            e.printStackTrace();
        }
        return new Response(body, statusCode);
    }
    @PostMapping("/join")
    public Response joinGame(@RequestBody JoinRequest request) {
        userDto = userService.findUser(request.getUsername());
        Game game = GamesContainer.addPlayerToGame(userDto, request);
        //        try {
        //TODO do i need to do this if I use annotations?
        //        }
        return new Response(body, statusCode);
    }

    @PostMapping("/{id}")
    public Response deal(@PathVariable int id, @RequestBody UserRequest request) throws ClassNotFoundException {
        userDto = userService.findUser(request.getUsername());
        game = GamesContainer.findGameById(id);
        if (game == null){
            throw new ClassNotFoundException("We could not find your game, sorry.");
        }
        List<Card> riverCards = game.startNextRound();
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

    @GetMapping("/{id}/bet/new-deal")
    public Response startNewDeal(@PathVariable int id){
        game = GamesContainer.findGameById(id);
        BetOptions options = game.startNewDeal();
        try {
            body = mapper.writeValueAsString(options);
        } catch (JsonProcessingException e) {
            body = e.getMessage();
            statusCode = 400;
            e.printStackTrace();
        }
        return new Response(body, statusCode);
    }

    @GetMapping("/{id}/bet")
    public Response getBetOptions(@PathVariable int id){
        game = GamesContainer.findGameById(id);
        BetOptions options = game.getBetOptions();
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
    public Response bet(@PathVariable int id, @RequestBody BetRequest request){
        game = GamesContainer.findGameById(id);
        return new Response(body, statusCode);
    }

}
