package com.brewster.poker;

import com.brewster.poker.bet.Action;
import com.brewster.poker.bet.Bet;
import com.brewster.poker.bet.BetAction;
import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.card.PokerHandEnum;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.request.BetRequest;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;

public class TestDataBuilder {
     public static final Action[] CHECK_ACTIONS = { Action.CHECK, Action.BET, Action.FOLD };
     public static final Action[] CALL_ACTIONS = { Action.CALL, Action.RAISE, Action.FOLD };

     public static BetOptions getBetOptions() {
          return new BetOptions(getPlayer(), CALL_ACTIONS, 10, 10);
     }
//
//     public static Bet getBet() {
//          return new BetAction(getPlayer(), n);
//     }
//
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
          Player player = new HumanPlayer("John", "jj@gmail.com");
          player.setCards(CardHandBuilder.buildPair());
          player.setPokerHand(PokerHandEnum.PAIR);
          return player;
     }
     public static Player getTwoPairPlayer(){
          Player player = getPlayer();
          player.setCards(CardHandBuilder.buildTwoPairWithSevenCards());
          player.setPokerHand(PokerHandEnum.TWO_PAIR);
          return player;
     }
}
