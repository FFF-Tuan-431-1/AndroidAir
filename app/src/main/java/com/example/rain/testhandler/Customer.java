package com.example.rain.testhandler;

/**
 * Created by rain on 2016/6/4.
 */
public class Customer{
    private String name;
    private int room;

    public Customer(String na, int ro){
        name = na;
        room = ro;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getRoom() {
        return room;
    }
}
