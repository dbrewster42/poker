package com.brewster.poker.controller;

import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.model.request.PlayerRequest;
import com.brewster.poker.model.response.Response;
import com.brewster.poker.service.PlayerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
public class PlayerController {
    private final PlayerService playerService;
    private final ObjectMapper mapper = new ObjectMapper();
    private PlayerDto playerDto;
    private String body;
    private int statusCode = 200;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/register")
    public Response register(@RequestBody PlayerRequest request) {
        System.out.println("-------------------------- " + request.getUsername() + request.getMoney());
        PlayerDto dto = new PlayerDto();
        BeanUtils.copyProperties(request, dto);

        try {
            playerDto = playerService.createPlayer(dto);
            body = mapper.writeValueAsString(playerDto);
        } catch (ConstraintViolationException e) {
            body = "That username is already taken. You must choose a unique username";
            statusCode = 400;
        }catch (DataIntegrityViolationException e) {
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
        return new Response(body, statusCode);
    }

    @PostMapping("/login")
    public Response login(@RequestBody PlayerRequest request) {
        playerDto = playerService.findPlayer(request.getUsername());
        try {
            body = mapper.writeValueAsString(playerDto);
        } catch (JsonProcessingException e) {
            body = e.getMessage();
            statusCode = 400;
            e.printStackTrace();
        }
        System.out.println(body + "____________________________________" + statusCode);
        return new Response(body, statusCode);
    }

    @PutMapping("buyin")
    public Response addMoney(@RequestBody PlayerRequest request){
        PlayerDto dto = new PlayerDto();
        BeanUtils.copyProperties(request, dto);

        playerDto = playerService.addMoneyToPlayer(dto);
        try {
            body = mapper.writeValueAsString(playerDto);
        } catch (JsonProcessingException e) {
            body = e.getMessage();
            statusCode = 400;
            e.printStackTrace();
        }
        return new Response(body, statusCode);
    }


}
