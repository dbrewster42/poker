package com.brewster.poker.controller;

import com.brewster.poker.model.GameEntity;
import com.brewster.poker.service.BetService;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.service.GameService;
import com.brewster.poker.service.OldGamesContainer;
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

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("game")
public class BetController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BetController.class);
    private final BetService betService;
    private final GameService gameService;
//    private GameService game;
//    private BetService betManager;
//    private OldGamesContainer gamesContainer;

//    public BetController(OldGamesContainer gamesContainer){
//        this.gamesContainer = gamesContainer;
//    }
    public BetController(BetService betService, GameService gameService){
        this.betService = betService;
        this.gameService = gameService;
    }


    @GetMapping("{id}/bet")
    public BetResponse getBetOptions(@PathVariable long id){
        LOGGER.info("Controller : getting betOptions");
        GameEntity game = gameService.findGame(id);
        BetOptions options = betService.manageComputerBets(game);

        return new BetResponse(options, game.getBetManagerEntity().getBetMessages());
    }
    //        GameService game = gamesContainer.findGameById(id);

//        BetOptions options = game.getBetManager().manageComputerBets();
//        return new BetResponse(options, game.getBetManager().getBetMessages());

    @PostMapping("{id}/bet")
    public BetResponse bet(@PathVariable long id, @RequestBody BetRequest request){
        LOGGER.info("Controller: Placing bet - {}", request.toString());
        GameEntity game = gameService.findGame(id);

        int userMoney = betService.placeBet(game, request);
        LOGGER.info("Controller: Bet has been placed - {}$ left", userMoney);
        return new BetResponse(game.isBet(), game.getBetManagerEntity().getBetMessages(), userMoney, game.isDealDone());
    }

//    @PostMapping("{id}/bet")
//    public BetResponse bet(@PathVariable long id, @RequestBody BetRequest request){
//        LOGGER.info("Controller: Placing bet - {}", request.toString());
//        GameService game = gamesContainer.findGameById(id);
//        BetService betManager = game.getBetManager();
//
//        int userMoney = betManager.placeBet(request);
//        LOGGER.info("Controller: Bet has been placed - {}$ left", userMoney);
//        return new BetResponse(game.isBet(), betManager.getBetMessages(), userMoney, game.isDealDone());
//    }

}
