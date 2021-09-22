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

public interface GameService {
    BetOptions startNewDeal();
    List<Card> deal();
    BetOptions getBetOptions();
    EndRoundResponse calculateWinningHand();
    NewGameResponse getNewGameResponse(UserDto userDto);
//    NewGameResponse getRestartGameResponse(UserDto userDto);

    void addPlayerToGame(HumanPlayer player);
    void setGameOver();

    BetService getBetManager();
    int getId();
    void setIsBet(boolean bet);
    boolean isBet();
    List<Player> getPlayers();
    int getOpenSlots();

    static GameService createNewTexasHoldEmGame(int id, HumanPlayer player, GameSettingsRequest settingsRequest){
        return new TexasHoldEmService(id, player, settingsRequest);
    }

    static GameService createNewTexasHoldEmGame(int id, List<Player> players, GameSettingsRequest settingsRequest){
        return new TexasHoldEmService(id, players, settingsRequest);
    }
}