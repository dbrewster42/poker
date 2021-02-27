package com.brewster.poker.game;

import com.brewster.poker.dto.PlayerDto;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static int gameID = 0;
    private static List<Game> allGames = new ArrayList<>();

    public static Game createGame(List<PlayerDto> players){
        Game game = new Game(gameID, players);
        gameID++;
        allGames.add(game);
        return game;
    }


    public static Game findGameById(int id){
        Game game = allGames.get(id);
        if (game.getId() != id){
            System.out.println("Error with game id. Attempting to fix");
            for (Game each : allGames){
                if (each.getId() == id){
                    game = each;
                    System.out.println("The error has been patched");
                }
            }
        }
        return game;
    }

    public static int getGameID() {
        return gameID;
    }

    public static List<Game> getAllGames() {
        return allGames;
    }
}
