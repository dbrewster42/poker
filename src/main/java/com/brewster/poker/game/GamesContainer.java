package com.brewster.poker.game;

import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.request.SettingsRequest;

import java.util.ArrayList;
import java.util.List;

public class GamesContainer {
    private static int gameID = 0;
    private static List<Game> allGames = new ArrayList<>();

    public static Game createGame(List<UserDto> users, SettingsRequest settingsRequest){
        List<Player> players = convertUsersToPlayers(users);
        Game game = new Game(gameID, players, settingsRequest);
        gameID++;
        allGames.add(game);
        return game;
    }

    private static List<Player> convertUsersToPlayers(List<UserDto> users){
        List<Player> players = new ArrayList<>();
        for (UserDto user : users){
            players.add(user.getPlayer());
        }
        return players;
    }


    public static Game findGameById(Integer id){
        //Game game = allGames.stream().filter(v -> v.getId() == id).findAny().orElse(null);
        Game game = allGames.get(id);
        if (game.getId() == id){
            return game;
        } else {
            for (Game i : allGames){
                if (i.getId() == id){
                    return i;
                }
            }
            return null;
        }
    }

    public static int getGameID() {
        return gameID;
    }

    public static List<Game> getAllGames() {
        return allGames;
    }
}
