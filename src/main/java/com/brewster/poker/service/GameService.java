package com.brewster.poker.service;

import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.response.GameResponse;
import com.brewster.poker.model.response.NewGameResponse;
import com.brewster.poker.player.HumanPlayer;

public interface GameService {
    GameEntity createGame(UserDto userDto, GameSettingsRequest settingsRequest, UserDto computerUser);
    GameEntity findGame(long id);
    NewGameResponse startNewDeal(GameEntity gameEntity, UserDto userDto);
    NewGameResponse startNewDeal(GameEntity gameEntity, String email);

    GameResponse deal(GameEntity gameEntity);
    NewGameResponse getNewGameResponse(GameEntity gameEntity, UserDto userDto);

    void addPlayerToGame(GameEntity gameEntity, HumanPlayer player);
    UserDto getThisUser(GameEntity gameEntity, String name);
    void saveGame(GameEntity gameEntity);

}