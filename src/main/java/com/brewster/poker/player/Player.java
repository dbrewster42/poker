package com.brewster.poker.player;

import com.brewster.poker.card.Card;
import com.brewster.poker.model.PlayerRequest;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Entity
public class Player {
    @Id
    private Integer id;
    @Column(nullable = false, unique = true)
    private String name;
    private int money;
    private List<Card> hand;
    @Column(updatable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;

    public Player(PlayerRequest request){
        name = request.getEmail();
        money = request.getMoney();
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }
}
