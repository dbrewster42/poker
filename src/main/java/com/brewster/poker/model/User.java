package com.brewster.poker.model;

import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.request.UserRequest;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

public class User {
    @Id
    private long id;
//    @Column(nullable = false, unique = true)
    private String username;
    private Integer money;
//    @Column(updatable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;

    public User(UserRequest request){
        username = request.getUsername();
        money = request.getMoney();
    }
    public User(UserDto dto){
        username = dto.getUsername();
        money = dto.getMoney();
    }
    public User(){ }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
