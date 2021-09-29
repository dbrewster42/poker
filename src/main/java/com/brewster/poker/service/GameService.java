package com.brewster.poker.service;

import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.response.GameResponse;
import com.brewster.poker.model.response.NewGameResponse;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;

import java.util.List;

public interface GameService {
    BetOptions startNewDeal();
    GameResponse deal();
    BetOptions getBetOptions();
    NewGameResponse getNewGameResponse(UserDto userDto);

    void addPlayerToGame(HumanPlayer player);
    void setGameOver();

    UserDto getUser(String name);
    BetService getBetManager();
    int getId();
    void setIsBet(boolean bet);
    boolean isBet();
    boolean isDealDone();
    List<Player> getPlayers();
    int getOpenSlots();
}