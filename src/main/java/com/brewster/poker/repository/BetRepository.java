package com.brewster.poker.repository;

import com.brewster.poker.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetRepository extends CrudRepository<Bet, Long> {
}
