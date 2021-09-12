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


    @GetMapping("/{id}/bet")
    public BetResponse getBetOptions(@PathVariable int id){
        System.out.println("Controller : getting betOptions");
        game = GamesContainer.findGameById(id);

        BetOptions options = game.getBetManager().manageComputerBets();
        return new BetResponse(options, game.getBetManager().getBetMessages());
    }

    @PostMapping("/{id}/bet")
    public BetResponse bet(@PathVariable int id, @RequestBody BetRequest request){
        System.out.println("Controller: Placing bet - " + request.toString());
        game = GamesContainer.findGameById(id);
        betManager = game.getBetManager();

        String message = betManager.placeBet(request);
        System.out.println("Controller: Bet has been placed - " + message);
        return new BetResponse(game.isBet(), betManager.getBetMessages(), betManager.getCurrentBettersMoney());
    }

}
