package com.brewster.poker.controller;

import com.brewster.poker.card.Deck;
import com.brewster.poker.model.GameRequest;
import com.brewster.poker.model.PlayerRequest;
import com.brewster.poker.model.Response;
import com.brewster.poker.player.Player;
import com.brewster.poker.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins =  {"http://localhost:3000/"})
public class Controller {
    private Player player;
    private Deck deck;
    private final PlayerService playerService;
//    private final PlayerRepository playerRepository;
//
//    public Controller(PlayerRepository playerRepository){
//        this.playerRepository = playerRepository;
//    }
    public Controller(PlayerService playerService){
        this.playerService = playerService;
    }

    @PostMapping("/login")
    public void loginNew(@RequestBody PlayerRequest request){
        System.out.println("-------------------------- " + request.getEmail() + request.getMoney());
        player = new Player(request);
    }

    @PutMapping("/login")
    public void loginOld(@RequestBody PlayerRequest request){
        //player = playerRepository.findByName(request.getEmail());
        player = playerService.findAndUpdatePlayer(request);

    }

    @PostMapping("/start")
    public List<Response> startGame(@RequestBody GameRequest request){
        List<Player> players = new ArrayList<>();
        List<Response> responses = new ArrayList<>();
        return responses;
    }

    @PostMapping("/")
    public List<Response> deal(){
        deck = new Deck();
        List<Response> responses = new ArrayList<>();
        return responses;
    }

}
