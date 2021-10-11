package com.brewster.poker.repository;

import com.brewster.poker.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;


@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    User findByUsername(String username);
}
