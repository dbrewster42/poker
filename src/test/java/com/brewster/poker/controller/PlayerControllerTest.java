package com.brewster.poker.controller;

import com.brewster.poker.dto.PlayerDto;
import com.brewster.poker.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PlayerControllerTest {
     private PlayerController sut;
     private PlayerService playerService;

     @BeforeEach
     void setUp() {
          playerService = mock(PlayerService.class);
          sut = new PlayerController(playerService);
     }

     @Test
     void register() {
         PlayerDto response = sut.register(createPlayerRequest());
         assertEquals("bob", response.getDisplayName());
         assertEquals(22, response.getMoney());
     }

     public PlayerDto createPlayerRequest(){
          return new PlayerDto("bob22@gmail.com", "bob", 22);
     }
}