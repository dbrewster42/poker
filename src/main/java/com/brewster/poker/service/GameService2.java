package com.brewster.poker.service;

import com.brewster.poker.bet.BetOptions;
import com.brewster.poker.card.Card;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.response.EndRoundResponse;
import com.brewster.poker.model.response.NewGameResponse;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;

import java.util.List;

public interface GameService2 {
    BetOptions startNewDeal();
    List<Card> deal();
    BetOptions getBetOptions();
    EndRoundResponse calculateWinningHand();
//    NewGameResponse getNewGameResponse(UserDto userDto);

//    void addPlayerToGame(HumanPlayer player);
    void setGameOver();

//    UserDto getUser(String name);
    BetService getBetManager();
    int getId();
    void setIsBet(boolean bet);
    boolean isBet();
    boolean isDealDone();
    List<Player> getPlayers();
    int getOpenSlots();

    static GameService2 createNewTexasHoldEmGame(int id, HumanPlayer player, GameSettingsRequest settingsRequest){
        return new TexasHoldEmService2(id, player, settingsRequest);
    }

    static GameService2 createNewTexasHoldEmGame(int id, List<Player> players, GameSettingsRequest settingsRequest){
        return new TexasHoldEmService2(id, players, settingsRequest);
    }
}