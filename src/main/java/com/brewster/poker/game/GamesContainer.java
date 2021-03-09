package com.brewster.poker.game;

import com.brewster.poker.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public class GamesContainer {
    private static int gameID = 0;
    private static List<Game> allGames = new ArrayList<>();

    public static Game createGame(List<UserDto> players){
        Game game = new Game(gameID, players);
        gameID++;
        allGames.add(game);
        return game;
    }


    public static Game findGameById(Integer id){
        //Game game = allGames.stream().filter(v -> v.getId() == id).findAny().orElse(null);
        for (Game i : allGames){
            if (i.getId() == id){
                return i;
            }
        }
        return null;
    }

    public static int getGameID() {
        return gameID;
    }

    public static List<Game> getAllGames() {
        return allGames;
    }
}
