package com.brewster.poker.service;

import com.brewster.poker.dto.UserDto;
import com.brewster.poker.exception.GameNotFoundException;
import com.brewster.poker.model.request.GameSettingsRequest;
import com.brewster.poker.model.request.JoinRequest;
import com.brewster.poker.player.ComputerPlayer;
import com.brewster.poker.player.HumanPlayer;
import com.brewster.poker.player.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class GamesContainer {
    private int gameID = 0;
    private List<GameService> allGames = new ArrayList<>();
    private UserService userService;
    private Random random = new Random();


    public GamesContainer(UserService userService) {
        this.userService = userService;
    }

    public GameService findGameById(Integer id){
        GameService game = allGames.get(id);
        if (game.getId() == id){
            return game;
        } else {
            return allGames.stream().filter(v -> v.getId() == id)
                    .findAny().orElseThrow(GameNotFoundException::new);
        }
    }

    public List<GameService> findAvailableGames(){
        return allGames.stream()
                .filter(v -> v.getOpenSlots() > 0)
                .collect(Collectors.toList());
    }

    public GameService addPlayerToGame(UserDto user, JoinRequest joinRequest){
        GameService game = findGameById(joinRequest.getGameId());
        game.addPlayerToGame(convertUserToPlayer(user, joinRequest.getDisplayName()));
        return game;
    }

    public GameService createGame(UserDto userDto, GameSettingsRequest settingsRequest){
        HumanPlayer player = convertUserToPlayer(userDto, settingsRequest.getDisplayName());
        GameService game = new TexasHoldEmService(gameID, player, settingsRequest, userService);
        gameID++;
        allGames.add(game);
        return game;
    }
    public GameService createGame(UserDto userDto, GameSettingsRequest settingsRequest, UserDto computerUser){
        List<Player> players = generateNComputerPlayers(settingsRequest.getNumberOfPlayers() - 1, computerUser);
        HumanPlayer player = convertUserToPlayer(userDto, settingsRequest.getDisplayName());
        GameService game = new TexasHoldEmService(gameID, players, settingsRequest, userService);
        gameID++;
        allGames.add(game);
        players.add(player);
        return game;
    }

    private HumanPlayer convertUserToPlayer(UserDto userDto, String displayName){
        HumanPlayer player = new HumanPlayer(displayName, userDto);
        userDto.setPlayer(player);
        return player;
    }

    public List<Player> generateNComputerPlayers(int n, UserDto computer){
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String displayName = "HAL" + random.nextInt(500);
            Player player = new ComputerPlayer(displayName, computer);
            players.add(player);
        }
        return players;
    }

    public int getGameID() {
        return gameID;
    }

    public List<GameService> getAllGames() {
        return allGames;
    }
}
