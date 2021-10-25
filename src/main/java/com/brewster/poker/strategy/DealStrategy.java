package com.brewster.poker.strategy;

import com.brewster.poker.card.Card;
import com.brewster.poker.dto.Dto;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.GameEntity;
import com.brewster.poker.model.response.GameResponse;
import com.brewster.poker.player.Player;

import java.util.List;

public interface DealStrategy {
     GameResponse dealGameCards(GameEntity gameEntity);
     void dealPlayerCards(List<Player> players, List<Card> cards);

     List<Dto> getPlayers(GameEntity gameEntity, UserDto userDto);
}
