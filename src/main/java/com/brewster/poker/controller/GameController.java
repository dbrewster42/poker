package com.brewster.poker.controller;

import com.brewster.poker.cards.Card;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.game.Game;
import com.brewster.poker.game.GamesContainer;
import com.brewster.poker.bets.BetOptions;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.request.JoinRequest;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.request.UserRequest;
import com.brewster.poker.model.response.BetResponse;
import com.brewster.poker.model.response.NewGameResponse;
import com.brewster.poker.model.response.Response;
import com.brewster.poker.player.HumanPlayer;
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
//    private String body;
//    private int statusCode = 200;
//    private final ObjectMapper mapper = new ObjectMapper();

    public GameController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public NewGameResponse createGame(@RequestBody GameSettingsRequest request) {
        userDto = userService.findUser(request.getUsername());
        System.out.println(request.toString());
        if (request.isFillWithComputerPlayers()){
            computerUser = userService.findUser("HAL");
            game = GamesContainer.createGame(userDto, request, computerUser);
        } else {
            game = GamesContainer.createGame(userDto, request);
        }
        List<Card> playerCards = userDto.getPlayer().getHand();
        //TODO return game.getGameResponse or construct here?
        //return game.getGameResponse();
        List<UserDto> users = game.getUsers();
        BetOptions options = game.startNewDeal();

        NewGameResponse response;
        if (options.getPlayer() == userDto.getPlayer()){
            response = new NewGameResponse(game.getId(), playerCards, users, options);
        } else {
            response = new NewGameResponse(game.getId(), playerCards, users);
        }

        return response;
    }
    @GetMapping("/{id}")
    public List<Card> deal(@PathVariable int id) {
        game = GamesContainer.findGameById(id);
        if (game == null){
            System.out.println("ERROR !!!!!!!!!!!!!!!!! ERROR !!!!!!!!!!!!!!!!! ERROR");
        }
        return game.startNextRound();
    }

    @GetMapping("/join")
    public List<Game> findGame() {
        return GamesContainer.findAvailableGames();
    }
    @PostMapping("/join")
    public Response joinGame(@RequestBody JoinRequest request) {
        userDto = userService.findUser(request.getUsername());
        Game game = GamesContainer.addPlayerToGame(userDto, request);
        //        try {
        //TODO do i need to do this if I use annotations?
        //        }
        return null;
    }
//    @GetMapping("/{id}")
//    public Response deal(@PathVariable int id) {
//        game = GamesContainer.findGameById(id);
//        if (game == null){
//            System.out.println("ERROR !!!!!!!!!!!!!!!!! ERROR !!!!!!!!!!!!!!!!! ERROR");
//        }
//        List<Card> riverCards = game.startNextRound();
//        try {
//            body = mapper.writeValueAsString(riverCards);
//        } catch (JsonProcessingException e) {
//            statusCode = 400;
//            body = e.getMessage();
//            e.printStackTrace();
//        }
//        return new Response(body, statusCode);
//    }
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

//    @GetMapping("/join")
//    public Response findGame() {
//        List<Game> openGames = GamesContainer.findAvailableGames();
//        try {
//            body = mapper.writeValueAsString(openGames);
//        } catch (JsonProcessingException e) {
//            body = e.getMessage();
//            statusCode = 400;
//            e.printStackTrace();
//        }
//        return new Response(body, statusCode);
//    }
//    @PostMapping("/join")
//    public Response joinGame(@RequestBody JoinRequest request) {
//        userDto = userService.findUser(request.getUsername());
//        Game game = GamesContainer.addPlayerToGame(userDto, request);
//        //        try {
//        //TODO do i need to do this if I use annotations?
//        //        }
//        return new Response(body, statusCode);
//    }

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

//    @GetMapping("/{id}/bet")
//    public Response getBetOptions(@PathVariable int id){
//        game = GamesContainer.findGameById(id);
//        BetOptions options = game.getBetOptions();
//        try {
//            body = mapper.writeValueAsString(options);
//        } catch (JsonProcessingException e) {
//            body = e.getMessage();
//            statusCode = 400;
//            e.printStackTrace();
//        }
//        return new Response(body, statusCode);
//    }
//    @GetMapping("/{id}/bet")
//    public BetOptions getBetOptions(@PathVariable int id, @RequestBody UserRequest request){
//        game = GamesContainer.findGameById(id);
//        //TODO good practice?
//        BetOptions options =  game.getBetOptions();
//        if (options.getPlayer() instanceof HumanPlayer){
//            return options;
//        } else {
//            options.getPlayer().placeBet();
//        }
//        return null;
//    }
//    @PostMapping("/{id}/bet")
//    public Response bet(@PathVariable int id, @RequestBody BetRequest request){
//        game = GamesContainer.findGameById(id);
//        userDto = userService.findUser(request.getUsername());
//
//        return new Response(body, statusCode);
//    }
//    //TODO should bet have own controller?
//    @PostMapping("/{id}/bet")
//    public BetResponse bet(@PathVariable int id, @RequestBody BetRequest request){
//        game = GamesContainer.findGameById(id);
//        userDto = userService.findUser(request.getUsername());
//        String message = game.placeBet(request);
//        //FIXME need all bets and way of checking if turn is over
//        return new BetResponse(true, message, null);
//    }

}
