package com.brewster.poker.repository;

import com.brewster.poker.dto.BetDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BetRepository extends CrudRepository<BetDto, Long> {
}
