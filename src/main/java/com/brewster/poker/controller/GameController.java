package com.brewster.poker.controller;

import com.brewster.poker.card.Card;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.request.UserRequest;
import com.brewster.poker.service.GameService;
import com.brewster.poker.service.GamesContainer;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.request.JoinRequest;
import com.brewster.poker.model.response.EndRoundResponse;
import com.brewster.poker.model.response.NewGameResponse;
import com.brewster.poker.player.Player;
import com.brewster.poker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("game")
public class GameController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
    private GameService game;
    private final UserService userService;
    private UserDto userDto;
    private UserDto computerUser;

    public GameController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public NewGameResponse createGame(@RequestBody GameSettingsRequest request) {
        LOGGER.info(request.toString());
        userDto = userService.findUser(request.getUsername());
        if (request.isFillWithComputerPlayers()){
            computerUser = userService.findUser("HAL");
            game = GamesContainer.createGame(userDto, request, computerUser);
        } else {
            game = GamesContainer.createGame(userDto, request);
            //TODO wait for players to join
        }
        game.startNewDeal();
        debug();

        return game.getNewGameResponse(userDto);
    }

    @PostMapping("{id}/restart")
    public NewGameResponse getNewRound(@PathVariable int id, @RequestBody UserRequest request){
        LOGGER.info(request.getUsername(), id);
        game = GamesContainer.findGameById(id);
        game.startNewDeal();
        userDto = game.getUser(request.getUsername());
        LOGGER.info(userDto.toString());
        return game.getNewGameResponse(userDto);
    }


    private void debug(){
        for (Player each : game.getPlayers()){
            LOGGER.info(each.getDisplayName());
            each.getHand().forEach(Card::show);
        }
        LOGGER.info("");
    }

    @GetMapping("{id}")
    public List<Card> deal(@PathVariable int id) {
        game = GamesContainer.findGameById(id);
        LOGGER.info("dealing card");

        return game.deal();
    }

    @GetMapping("{id}/winner")
    public EndRoundResponse calculateWinner(@PathVariable int id) {
        game = GamesContainer.findGameById(id);
        return game.calculateWinningHand();
    }

    @GetMapping("join")
    public List<GameService> findGame() {
        return GamesContainer.findAvailableGames();
    }

    @PostMapping("join")
    public void joinGame(@RequestBody JoinRequest request) {
        userDto = userService.findUser(request.getUsername());
        GameService game = GamesContainer.addPlayerToGame(userDto, request);
    }

}
