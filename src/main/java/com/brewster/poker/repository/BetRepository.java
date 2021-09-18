package com.brewster.poker.repository;

import com.brewster.poker.model.BetEntity;
import com.brewster.poker.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository extends CrudRepository<BetEntity, Long> {
     List<BetEntity> findAllByUser(User user);
}
