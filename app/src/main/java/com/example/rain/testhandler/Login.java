package com.example.rain.testhandler;

/**
 * Created by rain on 2016/6/4.
 */
public class Login {
    private String password;

    public Login(String s){
        password = s;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
