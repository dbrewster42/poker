package com.brewster.poker.controller;

import com.brewster.poker.card.Deck;
import com.brewster.poker.card.DeckBuilder;
import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.game.Game;
import com.brewster.poker.model.request.GameRequest;
import com.brewster.poker.model.request.PlayerRequest;
import com.brewster.poker.model.response.Response;
import com.brewster.poker.service.PlayerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.PropertyValueException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
public class PlayerController {
    private int id = 0;
    private final PlayerService playerService;
    private final ObjectMapper mapper = new ObjectMapper();
    Game game;
    private PlayerDto playerDto;
    private String body;
    private int statusCode = 200;
    private final Map<String, String> headers = createHeaders();


    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/login")
    public Response loginNew(@RequestBody PlayerRequest request) {
        System.out.println("-------------------------- " + request.getUsername() + request.getMoney());
        PlayerDto dto = new PlayerDto();
        BeanUtils.copyProperties(request, dto);

        try {
            playerDto = playerService.createPlayer(dto);
            body = mapper.writeValueAsString(playerDto);
            statusCode = 200;
        } catch (DataIntegrityViolationException e) {
            body = e.getMessage();
            statusCode = 500;
            e.printStackTrace();
        } catch (PropertyValueException e) {
            body = e.getMessage();
            statusCode = 500;
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            body = e.getMessage();
            statusCode = 400;
            e.printStackTrace();
        }
        System.out.println(body + "____________________________________" + statusCode);

        return new Response(body, headers, statusCode);
    }

    @PutMapping("/login")
    public void loginOld(@RequestBody PlayerRequest request) {
        //player = playerRepository.findByName(request.getEmail());
        PlayerDto dto = new PlayerDto();
        BeanUtils.copyProperties(request, dto);

        playerDto = playerService.findAndUpdatePlayer(dto);
    }

    //    @PostMapping("/start")
//    public List<Response> startGame(@RequestBody GameRequest request){
//       // List<Player> players = new ArrayList<>();
//        //players.add(play);
//        List<PlayerDto> players = playerService.startGame(playerDto, 4);
//
//        List<Response> responses = new ArrayList<>();
//        return responses;
//    }
    @PostMapping("/start")
    public Response startGame(@RequestBody GameRequest request) {
        List<PlayerDto> players = playerService.startGame(playerDto, 4);
        game = new Game(id++, players);
        try {
            body = mapper.writeValueAsString(players);
        } catch (JsonProcessingException e) {
            body = e.getMessage();
            statusCode = 400;
            e.printStackTrace();
        }
        System.out.println(id + " !!!!!!!!!!!!!!!!! " + body);
        return new Response(body, headers, statusCode);
    }

    private Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Methods", "*");
        headers.put("Access-Control-Allow-Headers", "*");
        return headers;
    }

}
