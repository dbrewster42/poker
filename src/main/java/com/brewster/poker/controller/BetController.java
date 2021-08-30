package com.brewster.poker.controller;

import com.brewster.poker.bets.BetManager;
import com.brewster.poker.bets.BetOptions;
import com.brewster.poker.game.Game;
import com.brewster.poker.game.GamesContainer;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.response.BetResponse;
import com.brewster.poker.player.ComputerPlayer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class BetController {
    private Game game;
    private BetManager betManager;

//    private UserDto userDto;
//    private UserService userService;
//
//    public BetController(UserService userService) {
//        this.userService = userService;
//    }

    @GetMapping("/{id}/bet")
    public BetOptions getBetOptions(@PathVariable int id){
        game = GamesContainer.findGameById(id);
        //TODO good practice?
        BetOptions options = game.getBetOptions();

        while (options.getPlayer() instanceof ComputerPlayer){
            options.getPlayer().placeBet(game.getRiverCards(), options, game.getBetManager());
            options = game.getBetOptions();
        }

        return options;
    }

    @PostMapping("/{id}/bet")
    public BetResponse bet(@PathVariable int id, @RequestBody BetRequest request){
        game = GamesContainer.findGameById(id);
        betManager = game.getBetManager();
//        userDto = userService.findUser(request.getUsername());

        String message = betManager.placeBet(request);
        return new BetResponse(betManager.isBet(), message, betManager.getBetsMade());
        //FIXME need to move to betOptions after return. new method that front end auto calls?
    }

}
