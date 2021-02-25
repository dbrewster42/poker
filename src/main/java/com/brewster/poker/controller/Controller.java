package com.brewster.poker.controller;

import com.brewster.poker.card.Deck;
import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.model.request.GameRequest;
import com.brewster.poker.model.request.PlayerRequest;
import com.brewster.poker.model.response.Response;
import com.brewster.poker.model.Player;
import com.brewster.poker.service.PlayerService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins =  {"http://localhost:3000/"})
public class Controller {
    //private Player player;
    private PlayerDto playerDto;
    private Deck deck;
    private final PlayerService playerService;

    public Controller(PlayerService playerService){
        this.playerService = playerService;
    }

    @PostMapping("/login")
    public Response loginNew(@RequestBody PlayerRequest request){
        System.out.println("-------------------------- " + request.getEmail() + request.getMoney());
        //player = new Player(request);
        PlayerDto dto = new PlayerDto();
        BeanUtils.copyProperties(request, dto);

        Response returnValue = new Response();
        try {
            PlayerDto playerDto = playerService.createPlayer(dto);
            returnValue.setMessage("success");
        } catch (Exception e){
            returnValue.setMessage(e.getMessage());
        }

        return returnValue;
    }

    @PutMapping("/login")
    public void loginOld(@RequestBody PlayerRequest request){
        //player = playerRepository.findByName(request.getEmail());
        PlayerDto dto = new PlayerDto();
        BeanUtils.copyProperties(request, dto);

        PlayerDto playerDto = playerService.findAndUpdatePlayer(dto);


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
