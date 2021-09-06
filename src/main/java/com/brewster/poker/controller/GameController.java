package com.brewster.poker.controller;

import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.card.Card;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.game.Game;
import com.brewster.poker.game.GamesContainer;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.request.JoinRequest;
import com.brewster.poker.model.response.NewGameResponse;
import com.brewster.poker.model.response.Response;
import com.brewster.poker.player.Player;
import com.brewster.poker.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/game")
public class GameController {
    private Game game;
    private final UserService userService;
    private UserDto userDto;
    private UserDto computerUser;

    public GameController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public NewGameResponse createGame(@RequestBody GameSettingsRequest request) {
        System.out.println(request.toString());
        userDto = userService.findUser(request.getUsername());
        if (request.isFillWithComputerPlayers()){
            computerUser = userService.findUser("HAL");
            game = GamesContainer.createGame(userDto, request, computerUser);
        } else {
            game = GamesContainer.createGame(userDto, request);
            //TODO wait for players to join
        }
//        BetOptions options = game.startNewDeal();
        game.startNewDeal();
        //TODO return game.getGameResponse or construct here?
        List<Card> playerCards = userDto.getPlayer().getHand();
        List<UserDto> users = game.getUsers();
        users.remove(userDto);
        BetOptions options = game.getBetManager().manageComputerBets();

        return new NewGameResponse(game.getId(), playerCards, users, options);
    }

    @GetMapping("/{id}")
    public List<Card> deal(@PathVariable int id) {
        System.out.println("dealing card");
        game = GamesContainer.findGameById(id);
        //TODO prevent repeat deals. bet must go first.
        for (Player each : game.getPlayers()){
            System.out.println(each.getDisplayName());
            each.getHand().forEach(Card::show);
        }
        System.out.println();
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
//    @PostMapping("/{id}/bet")
//    public BetResponse bet(@PathVariable int id, @RequestBody BetRequest request){
//        game = GamesContainer.findGameById(id);
//        userDto = userService.findUser(request.getUsername());
//        String message = game.placeBet(request);
//        //FIXME need all bets and way of checking if turn is over
//        return new BetResponse(true, message, null);
//    }

}
