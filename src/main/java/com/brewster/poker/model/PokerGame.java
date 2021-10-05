package com.brewster.poker.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class PokerGame {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private int id;
//     @OneToMany
//     private User user;
//     @OneToMany
//     private BetEntity betEntity;
//     //private Outcome outcome;
}
