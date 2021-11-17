package com.example.quizrun.MainModel;

public class User {

    private String name , email , referCode;
    private long coins;


    public User(String nam, String email, String referCode, long coins) {
        this.name = nam;
        this.email = email;
        this.referCode = referCode;
        this.coins = coins;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String nam) {
        this.name = nam;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }
}
