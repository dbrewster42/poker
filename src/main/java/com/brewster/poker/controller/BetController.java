package com.brewster.poker.controller;

import com.brewster.poker.model.BetEntity;
import com.brewster.poker.model.request.UserRequest;
import com.brewster.poker.service.BetService;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.service.GameService;
import com.brewster.poker.game.GamesContainer;
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
    private GameService game;
    private BetService betManager;

    @GetMapping("{id}/bet")
    public BetResponse getBetOptions(@PathVariable int id){
        LOGGER.info("Controller : getting betOptions");
        game = GamesContainer.findGameById(id);

        BetOptions options = game.getBetManager().manageComputerBets();
        return new BetResponse(options, game.getBetManager().getBetMessages());
    }

    @PostMapping("{id}/bet")
    public BetResponse bet(@PathVariable int id, @RequestBody BetRequest request){
        LOGGER.info("Controller: Placing bet - " + request.toString());
        game = GamesContainer.findGameById(id);
        betManager = game.getBetManager();

        betManager.placeBet(request);
        LOGGER.info("Controller: Bet has been placed");
        return new BetResponse(game.isBet(), betManager.getBetMessages(), betManager.getCurrentBettersMoney());
    }

    @GetMapping("getUserBets")
    public List<BetEntity> getUserBets(@RequestBody UserRequest request){
        return betManager.getUserBets(request.getUsername());
    }

}
