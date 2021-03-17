package com.brewster.poker.game;

import com.brewster.poker.dto.UserDto;
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
    private static List<Game> allGames = new ArrayList<>();
    private static UserDto computer;

    public static Game findGameById(Integer id){
        Game game = allGames.get(id);
        if (game.getId() == id){
            return game;
        } else {
            return allGames.stream().filter(v -> v.getId() == id)
                    .findAny().orElse(null);
        }
    }

    public static List<Game> findAvailableGames(){
        return allGames.stream()
                .filter(v -> v.getOpenSlots() > 0)
                .collect(Collectors.toList());
    }

    public static Game addPlayerToGame(UserDto user, JoinRequest joinRequest){
        Game game = findGameById(joinRequest.getGameId());
        game.addPlayerToGame(convertUserToPlayer(user, joinRequest.getDisplayName()));
        return game;
    }

    public static Game createGame(UserDto userDto, GameSettingsRequest settingsRequest){
        HumanPlayer player = convertUserToPlayer(userDto, settingsRequest.getDisplayName());
        Game game = new Game(gameID, player, settingsRequest);
        gameID++;
        allGames.add(game);
        return game;
    }
    public static Game createGame(UserDto userDto, GameSettingsRequest settingsRequest, UserDto computerUser){
        computer = computerUser;
        List<Player> players = generateNComputerPlayers(settingsRequest.getNumberOfPlayers() - 1);
        HumanPlayer player = convertUserToPlayer(userDto, settingsRequest.getDisplayName());
        players.add(player);
        Game game = new Game(gameID, players, settingsRequest);
        gameID++;
        allGames.add(game);
        return game;
    }

    private static HumanPlayer convertUserToPlayer(UserDto userDto, String displayName){
        HumanPlayer player = new HumanPlayer(displayName, userDto);
        return player;
    }

    public static List<Player> generateNComputerPlayers(int n){
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

    public static List<Game> getAllGames() {
        return allGames;
    }
}
