package com.brewster.poker.game;

import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.request.GameSettingsRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GamesContainer {
    private static int gameID = 0;
    private static List<Game> allGames = new ArrayList<>();


    public static List<Game> findAvailableGames(){
        return allGames.stream()
                .filter(v -> v.getNumberOfPlayers() < v.getDesiredNumberOfPlayers())
                .collect(Collectors.toList());
    }

    public static Game addPlayerToGame(UserDto user, int gameID){
        Game game = findGameById(gameID);
        game.addPlayerToGame(convertUserToPlayer(user));
        return game;
    }

    public static Game createGame(List<UserDto> users, GameSettingsRequest settingsRequest){
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

    private static Player convertUserToPlayer(UserDto userDto){
        //todo need to create player. this won't work
        return userDto.getPlayer();
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
