package com.epitech.jibb.canscounter.models;

public class User {

    private String _username;
    private int _img;
    private int _cans;

    public User(String username, int url, int cans) {
        _username = username;
        _img = url;
        _cans = cans;
    }

    public String getUsername() {
        return _username;
    }

    public int getImg() {
        return _img;
    }

    public int getCans() {
        return _cans;
    }

    public void setUsername(String username) {
        _username = username;
    }

    public void setImg(int url) {
        _img = url;
    }

    public void setCans(int cans) {
        _cans = cans;
    }
}
