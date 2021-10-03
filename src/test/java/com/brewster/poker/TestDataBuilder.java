package com.brewster.poker;

import com.brewster.poker.bet.Action;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;

public class TestDataBuilder {
     public static GameSettingsRequest getGameSettingsRequest(){
          GameSettingsRequest gameSettingsRequest = new GameSettingsRequest();
          gameSettingsRequest.setFillWithComputerPlayers(true);
          gameSettingsRequest.setNumberOfPlayers(4);
          gameSettingsRequest.setBigBlind(5);
          gameSettingsRequest.setDisplayName("BREWSTER");
          return gameSettingsRequest;
     }

     public static BetRequest getCheckBetRequest(int amount){
          BetRequest betRequest = new BetRequest();
          betRequest.setBetAmount(amount);
          betRequest.setUsername("BREWSTER");
          betRequest.setAction(Action.CALL.name());
          return betRequest;
     }

     public static BetRequest getBetBetRequest(){
          BetRequest betRequest = new BetRequest();
          betRequest.setBetAmount(20);
          betRequest.setUsername("BREWSTER");
          betRequest.setAction(Action.BET.name());
          return betRequest;
     }

     public static Player getPlayer(){
          UserDto userDto = new UserDto();
          userDto.setMoney(100);
          Player player = new HumanPlayer("John", userDto);
          player.setCards(CardHandBuilder.buildPair());
          return player;
     }
     public static Player getTwoPairPlayer(){
          Player player = getPlayer();
          player.setCards(CardHandBuilder.buildTwoPairWithSevenCards());
          return player;
     }
}
