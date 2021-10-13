package com.brewster.poker.model;

import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.request.UserRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

public class User {
    @Id
    @Indexed(unique = true)
    private String id;
    @Indexed(unique = true)
    private String email;
    private Integer money;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;

    public User(UserRequest request){
        email = request.getEmail();
        money = request.getMoney();
    }
    public User(UserDto dto){
        email = dto.getEmail();
        money = dto.getMoney();
    }
    public User(){ }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email='" + email + '\'' + ", money=" + money + '}';
    }
}
