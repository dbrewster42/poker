package com.brewster.poker.model;

import com.brewster.poker.model.request.PlayerRequest;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String name;
    private int money;
    @Column(updatable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;

    public Player(PlayerRequest request){
        name = request.getEmail();
        money = request.getMoney();
    }
    public Player(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }
}
