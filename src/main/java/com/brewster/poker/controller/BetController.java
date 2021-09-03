package com.brewster.poker.controller;

import com.brewster.poker.bet.BetManager;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.game.Game;
import com.brewster.poker.game.GamesContainer;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.response.BetResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/game")
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
        System.out.println("Controller : getting betOptions");
        game = GamesContainer.findGameById(id);
//        BetOptions options = game.getBetOptions();;
//        BetOptions options = game.getBetManager().manageComputerBets();

        return game.getBetManager().manageComputerBets();
    }

    @PostMapping("/{id}/bet")
    public BetResponse bet(@PathVariable int id, @RequestBody BetRequest request){
        System.out.println("Controller: Placing bet - " + request.toString());
        game = GamesContainer.findGameById(id);
        betManager = game.getBetManager();
//        userDto = userService.findUser(request.getUsername());

        String message = betManager.placeBet(request);
        System.out.println("Controller: Bet has been placed - " + message);
        return new BetResponse(betManager.isBet(), message, betManager.getBetsMade());
        //FIXME need to move to betOptions after return. new method that front end auto calls?
    }

}
