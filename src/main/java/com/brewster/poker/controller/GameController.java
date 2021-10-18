package com.brewster.poker.controller;

import com.brewster.poker.dto.UserDto;
import com.brewster.poker.exception.GameNotFoundException;
import com.brewster.poker.exception.UserNotFoundException;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.request.JoinRequest;
import com.brewster.poker.model.request.UserRequest;
import com.brewster.poker.model.response.GameResponse;
import com.brewster.poker.model.response.NewGameResponse;
import com.brewster.poker.service.GameService;
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
    private GameService gameService;
    private final UserService userService;
    private UserDto userDto;
    private UserDto computerUser;

    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @PostMapping
    public NewGameResponse createGame(@RequestBody GameSettingsRequest request) {
        LOGGER.info(request.toString());
        userDto = userService.findUserDtoByEmail(request.getUsername());
//        if (request.isFillWithComputerPlayers()){
//            computerUser = userService.findUser("HAL");
//        } else {
//        }
        GameEntity gameEntity = gameService.createGame(userDto, request, userService.findUserDtoByEmail("HAL"));
        LOGGER.info("saved game {}", gameEntity);
        return gameService.startNewDeal(gameEntity, userDto);
    }

    @PostMapping("{id}/restart")
    public NewGameResponse getNewRound(@PathVariable long id, @RequestBody UserRequest request){
        LOGGER.info("{} has requested a new game for {}", request.getEmail(), id);
        GameEntity gameEntity = gameService.findGame(id);

        return gameService.startNewDeal(gameEntity, request.getEmail());
    }

    @GetMapping("{id}")
    public GameResponse deal(@PathVariable long id) {
        LOGGER.info("dealing card");
        GameEntity gameEntity = gameService.findGame(id);

        return gameService.deal(gameEntity);
    }

    @GetMapping("join")
    public List<GameService> findGame() {
        return null;
    }

    @PostMapping("join")
    public void joinGame(@RequestBody JoinRequest request) {
        userDto = userService.findUserDtoByEmail(request.getUsername());
//        gameService.addPlayerToGame();
    }

    @GetMapping("exception")
    public void throwException(){
        throw new GameNotFoundException();
    }

    @GetMapping("exception2")
    public void throwException2(){
        throw new UserNotFoundException();
    }
}
