package com.brewster.poker;

import com.brewster.poker.bet.Action;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.request.GameSettingsRequest;

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
}