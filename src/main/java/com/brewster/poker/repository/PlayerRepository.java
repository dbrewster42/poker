package com.brewster.poker.repository;

import com.brewster.poker.model.PlayerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PlayerRepository extends MongoRepository<PlayerEntity, String> {
     Optional<PlayerEntity> findByUsername(String name);

}
