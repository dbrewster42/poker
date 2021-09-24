package com.brewster.poker.model;

import com.brewster.poker.card.Card;
import com.brewster.poker.player.Player;
import com.brewster.poker.service.TexasHoldEmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class GameEntity {
     private static final Logger LOGGER = LoggerFactory.getLogger(TexasHoldEmService.class);
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private long id;

     private List<Player> players;
     private List<Card> cards;
     private List<Card> riverCards = new ArrayList<>();
     private boolean isBet;
     private boolean isDealDone;

     private int desiredNumberOfPlayers;
     private int openSlots;
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date createdAt;
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     private Date updatedAt;
//     @OneToOne
//     @Column(nullable = false)
//     private BetManagerEntity betManagerEntity;


     private int bigBlind;
     private int turnNumber;
     private int turnsLeftInRound;
     private List<Player> activeBetters;
     private int pot = 0;
     private int betAmount;
     private int bigBlindTurn = -1;
     @OneToMany
     private List<BetEntity> bets;

//     private List<String> betMessages;
//    private Integer maxBet;

}
