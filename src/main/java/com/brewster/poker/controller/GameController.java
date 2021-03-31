package com.brewster.poker.controller;

import com.brewster.poker.cards.Card;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.game.Game;
import com.brewster.poker.game.GamesContainer;
import com.brewster.poker.bets.BetOptions;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.request.JoinRequest;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.response.GameResponse;
import com.brewster.poker.model.response.NewGameResponse;
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
    private UserDto computerUser;
    private String body;
    private int statusCode = 200;
    private final ObjectMapper mapper = new ObjectMapper();

    public GameController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public NewGameResponse createGame(@RequestBody GameSettingsRequest request) {
        userDto = userService.findUser(request.getUsername());
        System.out.println(request.toString());
        if (request.isFillWithComputerPlayers()){
            System.out.println("true");
            computerUser = userService.findUser("HAL");
            game = GamesContainer.createGame(userDto, request, computerUser);
        } else {
            System.out.println("false");
            game = GamesContainer.createGame(userDto, request);
        }
//        GameResponse gameResponse = game.getGameResponse();
//        System.out.println(game.toString());
        BetOptions options = game.startNewDeal();
        List<Card> playerCards = userDto.getPlayer().getHand();
        playerCards.stream().forEach(v -> v.show());
        //TODO only return betOptions if player == player
//        try {
//            //body =List<MyClass> myObjects = Arrays.asList(mapper.readValue(json, MyClass[].class))
//            body = mapper.writeValueAsString(gameResponse);
//            body += mapper.writeValueAsString(options);
//            body += mapper.writeValueAsString(playerCards);
//        } catch (JsonProcessingException e) {
//            body = e.getMessage();
//            statusCode = 400;
//            e.printStackTrace();
//        }
//        System.out.println(game.getId() + " !!!!!!!!!!!!!!!!! " + body);
//        Response response = new Response(body, statusCode);
        NewGameResponse response = new NewGameResponse(game.getId(), playerCards, game.getUsers(), options);
//        response.setGameId(game.getId());
        return response;
    }

    @GetMapping("/{id}")
    public Response deal(@PathVariable int id) {
        game = GamesContainer.findGameById(id);
        if (game == null){
            System.out.println("ERROR !!!!!!!!!!!!!!!!! ERROR !!!!!!!!!!!!!!!!! ERROR");
        }
        List<Card> riverCards = game.startNextRound();
        try {
            body = mapper.writeValueAsString(riverCards);
        } catch (JsonProcessingException e) {
            statusCode = 400;
            body = e.getMessage();
            e.printStackTrace();
        }
        return new Response(body, statusCode);
    }
//    @GetMapping("/{id}/new-deal")
//    public Response startNewDeal(@PathVariable int id){
//        game = GamesContainer.findGameById(id);
//        BetOptions options = game.startNewDeal();
//        try {
//            body = mapper.writeValueAsString(options);
//        } catch (JsonProcessingException e) {
//            body = e.getMessage();
//            statusCode = 400;
//            e.printStackTrace();
//        }
//        return new Response(body, statusCode);
//    }

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

//    @PostMapping("/{id}")
//    public Response deal(@PathVariable int id, @RequestBody UserRequest request) {
//        userDto = userService.findUser(request.getUsername());
//        game = GamesContainer.findGameById(id);
//        if (game == null){
//            System.out.println("ERROR !!!!!!!!!!!!!!!!! ERROR !!!!!!!!!!!!!!!!! ERROR");
//        }
//        List<Card> riverCards = game.startNextRound();
//        try {
//            body = mapper.writeValueAsString(riverCards);
//            body += mapper.writeValueAsString(userDto);
//        } catch (JsonProcessingException e) {
//            statusCode = 400;
//            body = e.getMessage();
//            e.printStackTrace();
//        }
//        return new Response(body, statusCode);
//    }

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
