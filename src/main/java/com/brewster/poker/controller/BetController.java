package com.brewster.poker.controller;

import com.brewster.poker.model.BetEntity;
import com.brewster.poker.model.request.UserRequest;
import com.brewster.poker.service.BetService;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.service.GameService;
import com.brewster.poker.service.GamesContainer;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.response.BetResponse;

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
public class BetController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BetController.class);
//    private GameService game;
//    private BetService betManager;
    private GamesContainer gamesContainer;

    public BetController(GamesContainer gamesContainer){
        this.gamesContainer = gamesContainer;
    }

    @GetMapping("{id}/bet")
    public BetResponse getBetOptions(@PathVariable int id){
        LOGGER.info("Controller : getting betOptions");
        GameService game = gamesContainer.findGameById(id);

        BetOptions options = game.getBetManager().manageComputerBets();
        return new BetResponse(options, game.getBetManager().getBetMessages());
    }

    @PostMapping("{id}/bet")
    public BetResponse bet(@PathVariable int id, @RequestBody BetRequest request){
        LOGGER.info("Controller: Placing bet - {}", request.toString());
        GameService game = gamesContainer.findGameById(id);
        BetService betManager = game.getBetManager();

        int userMoney = betManager.placeBet(request);
        LOGGER.info("Controller: Bet has been placed - {}$ left", userMoney);
        return new BetResponse(game.isBet(), betManager.getBetMessages(), userMoney, game.isDealDone());
    }

}
