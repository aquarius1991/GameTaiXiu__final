package com.example.dark.gametaixiuv2.DTO;

/**
 * Created by Dark on 5/24/2017.
 */

public class Player {
    private int id;
    private String username;
    private String password;
    private int money;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getMoney() {
        return money;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Player(int id, String username, String password, int money) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.money = money;
    }

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
