package com.brewster.poker.repository;

import com.brewster.poker.model.GameEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<GameEntity, Long> {
}
