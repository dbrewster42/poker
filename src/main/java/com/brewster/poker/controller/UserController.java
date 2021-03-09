package com.brewster.poker.controller;

import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.request.UserRequest;
import com.brewster.poker.model.response.Response;
import com.brewster.poker.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
    private final UserService userService;
    private final ObjectMapper mapper = new ObjectMapper();
    private UserDto userDto;
    private String body;
    private int statusCode = 200;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Response register(@RequestBody UserRequest request) {
        System.out.println("-------------------------- " + request.getUsername() + request.getMoney());
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(request, dto);

        try {
            userDto = userService.createUser(dto);
            body = mapper.writeValueAsString(userDto);
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
    public Response login(@RequestBody UserRequest request) {
        userDto = userService.findUser(request.getUsername());
        try {
            body = mapper.writeValueAsString(userDto);
        } catch (JsonProcessingException e) {
            body = e.getMessage();
            statusCode = 400;
            e.printStackTrace();
        }
        System.out.println(body + "____________________________________" + statusCode);
        return new Response(body, statusCode);
    }

    @PutMapping("buyin")
    public Response addMoney(@RequestBody UserRequest request){
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(request, dto);

        userDto = userService.addMoneyToUser(dto);
        try {
            body = mapper.writeValueAsString(userDto);
        } catch (JsonProcessingException e) {
            body = e.getMessage();
            statusCode = 400;
            e.printStackTrace();
        }
        return new Response(body, statusCode);
    }


}
