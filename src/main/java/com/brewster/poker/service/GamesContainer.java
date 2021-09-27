package com.brewster.poker.service;

import com.brewster.poker.dto.UserDto;
import com.brewster.poker.exception.GameNotFoundException;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.request.JoinRequest;
import com.brewster.poker.player.ComputerPlayer;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GamesContainer {
    private static int gameID = 0;
    private static List<GameService2> allGames = new ArrayList<>();
//    private static UserDto computer;

    public static GameService2 findGameById(Integer id){
        GameService2 game = allGames.get(id);
        if (game.getId() == id){
            return game;
        } else {
            return allGames.stream().filter(v -> v.getId() == id)
                    .findAny().orElseThrow(() -> new GameNotFoundException("Game could not be found"));
        }
    }

    public static List<GameService2> findAvailableGames(){
        return allGames.stream()
                .filter(v -> v.getOpenSlots() > 0)
                .collect(Collectors.toList());
    }

//    public static GameService2 addPlayerToGame(UserDto user, JoinRequest joinRequest){
//        GameService2 game = findGameById(joinRequest.getGameId());
//        game.addPlayerToGame(convertUserToPlayer(user, joinRequest.getDisplayName()));
//        return game;
//    }

    public static GameService2 createGame(UserDto userDto, GameSettingsRequest settingsRequest){
        HumanPlayer player = convertUserToPlayer(userDto, settingsRequest.getDisplayName());
        GameService2 game = GameService2.createNewTexasHoldEmGame(gameID, player, settingsRequest);
        gameID++;
        allGames.add(game);
        return game;
    }
    public static GameService2 createGame(UserDto userDto, GameSettingsRequest settingsRequest, UserDto computerUser){
//        computer = computerUser;
        List<Player> players = generateNComputerPlayers(settingsRequest.getNumberOfPlayers() - 1, computerUser);
        HumanPlayer player = convertUserToPlayer(userDto, settingsRequest.getDisplayName());
        GameService2 game = GameService2.createNewTexasHoldEmGame(gameID, players, settingsRequest);
        gameID++;
        allGames.add(game);
        players.add(player);
        return game;
    }
//    public static GameService createNewGame(PlayerDto userDto, GameSettingsRequest settingsRequest){
//        HumanPlayer player = convertUserToPlayer(userDto, settingsRequest.getDisplayName());
//        GameService game = GameService.createNewTexasHoldEmGame(gameID, player, settingsRequest);
//        return game;
//    }
    public static GameService2 fillGameWithCPs(UserDto userDto, GameSettingsRequest settingsRequest, UserDto computerUser){
        List<Player> players = generateNComputerPlayers(settingsRequest.getNumberOfPlayers() - 1, computerUser);
        HumanPlayer player = convertUserToPlayer(userDto, settingsRequest.getDisplayName());
        GameService2 game = GameService2.createNewTexasHoldEmGame(gameID, players, settingsRequest);
        gameID++;
        allGames.add(game);
        players.add(player);
        return game;
    }
    private static HumanPlayer convertUserToPlayer(UserDto userDto, String displayName){
        HumanPlayer player = new HumanPlayer(displayName, userDto);
        userDto.setPlayer(player);
        return player;
    }

    public static List<Player> generateNComputerPlayers(int n, UserDto computer){
        List<Player> players = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            String displayName = "HAL" + random.nextInt(500);
            Player player = new ComputerPlayer(displayName, computer);
            players.add(player);
        }
        return players;
    }

    public static int getGameID() {
        return gameID;
    }

    public static List<GameService2> getAllGames() {
        return allGames;
    }
}
