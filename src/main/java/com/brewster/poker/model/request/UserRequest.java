package com.brewster.poker.model.request;

public class UserRequest {
    private String email;
    private int money;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String email){ this.email = email; }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "UserRequest{" + "username='" + email + '\'' + ", money=" + money + '}';
    }
}
