package com.brewster.poker.dto;

import com.brewster.poker.game.Player;

public class UserDto {
    private Integer id;
    private String username;
    private Integer money;
    private Player player;
//    private List<Card> hand;
//    private Bet bet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    //    public List<Card> getHand() {
//        return hand;
//    }
//
//    public void setHand(List<Card> hand) {
//        this.hand = hand;
//    }
//
//    public void dealCard(Card card){ this.hand.add(card); }
//
//    public Bet getBet() {
//        return bet;
//    }
//
//    public void setBet(Bet bet) {
//        this.bet = bet;
//    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", money=" + money +
                '}';
    }
}
