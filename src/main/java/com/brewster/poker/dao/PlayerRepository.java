package com.brewster.poker.dao;

import com.brewster.poker.player.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
    Player findByName(String name);
}
