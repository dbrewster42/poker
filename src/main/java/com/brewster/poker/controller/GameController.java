package com.brewster.poker.controller;

import com.brewster.poker.card.Card;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.game.Game;
import com.brewster.poker.game.GamesContainer;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.request.JoinRequest;
import com.brewster.poker.model.response.EndRoundResponse;
import com.brewster.poker.model.response.NewGameResponse;
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
        game.startNewDeal();

        return game.getNewGameResponse(userDto);
    }

    @GetMapping("/{id}")
    public List<Card> deal(@PathVariable int id) {
        game = GamesContainer.findGameById(id);
        System.out.println("dealing card");
        for (Player each : game.getPlayers()){
            System.out.println(each.getDisplayName());
            each.getHand().forEach(Card::show);
        }
        System.out.println();
        return game.startNextRound();
    }

    @GetMapping("/{id}/winner")
    public EndRoundResponse calculateWinner(@PathVariable int id) {
        game = GamesContainer.findGameById(id);
        return game.calculateWinningHand();
    }

    @GetMapping("/join")
    public List<Game> findGame() {
        return GamesContainer.findAvailableGames();
    }

    @PostMapping("/join")
    public void joinGame(@RequestBody JoinRequest request) {
        userDto = userService.findUser(request.getUsername());
        Game game = GamesContainer.addPlayerToGame(userDto, request);
    }

}
